import { useState, useEffect } from 'react';
import Head from 'next/head';
import { useRouter } from 'next/router';
import { auth } from '../../lib/firebase';
import { wooService } from '../../lib/wooApi';
import Layout from '../../components/Layout';
import DashboardStats from '../../components/DashboardStats';
import SalesChart from '../../components/SalesChart';
import RecentOrders from '../../components/RecentOrders';
import TopProducts from '../../components/TopProducts';
import { toast } from 'react-hot-toast';
import {
  CurrencyDollarIcon,
  ShoppingBagIcon,
  UsersIcon,
  ChartBarIcon,
} from '@heroicons/react/24/outline';

interface DashboardData {
  todaysSales: number;
  weeklySales: number;
  monthlySales: number;
  totalProducts: number;
  totalOrders: number;
  totalCustomers: number;
  recentOrders: any[];
  topProducts: any[];
  salesChartData: any[];
}

export default function Dashboard() {
  const [dashboardData, setDashboardData] = useState<DashboardData>({
    todaysSales: 0,
    weeklySales: 0,
    monthlySales: 0,
    totalProducts: 0,
    totalOrders: 0,
    totalCustomers: 0,
    recentOrders: [],
    topProducts: [],
    salesChartData: [],
  });
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<any>(null);
  const router = useRouter();

  useEffect(() => {
    const unsubscribe = auth.onAuthStateChanged((user) => {
      if (user) {
        setUser(user);
        loadDashboardData();
      } else {
        router.push('/');
      }
    });

    return () => unsubscribe();
  }, [router]);

  const loadDashboardData = async () => {
    setIsLoading(true);
    try {
      // Fetch all dashboard data in parallel
      const [products, orders, customers, salesReport] = await Promise.all([
        wooService.getProducts({ per_page: 100 }).catch(() => []),
        wooService.getOrders({ per_page: 100 }).catch(() => []),
        wooService.getCustomers({ per_page: 100 }).catch(() => []),
        wooService.getSalesReport().catch(() => []),
      ]);

      // Calculate today's sales
      const today = new Date().toISOString().split('T')[0];
      const todaysOrders = orders.filter(order => 
        order.date_created.startsWith(today) && order.status === 'completed'
      );
      const todaysSales = todaysOrders.reduce((sum, order) => sum + parseFloat(order.total), 0);

      // Calculate weekly sales
      const weekAgo = new Date();
      weekAgo.setDate(weekAgo.getDate() - 7);
      const weeklyOrders = orders.filter(order => 
        new Date(order.date_created) >= weekAgo && order.status === 'completed'
      );
      const weeklySales = weeklyOrders.reduce((sum, order) => sum + parseFloat(order.total), 0);

      // Calculate monthly sales
      const monthAgo = new Date();
      monthAgo.setMonth(monthAgo.getMonth() - 1);
      const monthlyOrders = orders.filter(order => 
        new Date(order.date_created) >= monthAgo && order.status === 'completed'
      );
      const monthlySales = monthlyOrders.reduce((sum, order) => sum + parseFloat(order.total), 0);

      // Get recent orders (last 10)
      const recentOrders = orders
        .sort((a, b) => new Date(b.date_created).getTime() - new Date(a.date_created).getTime())
        .slice(0, 10);

      // Calculate top products
      const productSales: { [key: number]: number } = {};
      orders.forEach(order => {
        order.line_items.forEach(item => {
          productSales[item.product_id] = (productSales[item.product_id] || 0) + item.quantity;
        });
      });

      const topProducts = products
        .map(product => ({
          ...product,
          totalSold: productSales[product.id] || 0,
        }))
        .sort((a, b) => b.totalSold - a.totalSold)
        .slice(0, 5);

      // Generate sales chart data (last 7 days)
      const salesChartData = [];
      for (let i = 6; i >= 0; i--) {
        const date = new Date();
        date.setDate(date.getDate() - i);
        const dateStr = date.toISOString().split('T')[0];
        
        const dayOrders = orders.filter(order => 
          order.date_created.startsWith(dateStr) && order.status === 'completed'
        );
        const daySales = dayOrders.reduce((sum, order) => sum + parseFloat(order.total), 0);
        
        salesChartData.push({
          date: date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' }),
          sales: daySales,
          orders: dayOrders.length,
        });
      }

      setDashboardData({
        todaysSales,
        weeklySales,
        monthlySales,
        totalProducts: products.length,
        totalOrders: orders.length,
        totalCustomers: customers.length,
        recentOrders,
        topProducts,
        salesChartData,
      });
    } catch (error) {
      console.error('Error loading dashboard data:', error);
      toast.error('Failed to load dashboard data');
    } finally {
      setIsLoading(false);
    }
  };

  if (!user) {
    return null; // Will redirect to login
  }

  const stats = [
    {
      name: "Today's Sales",
      value: `Rp ${dashboardData.todaysSales.toLocaleString()}`,
      icon: CurrencyDollarIcon,
      change: '+12%',
      changeType: 'positive',
    },
    {
      name: 'Total Products',
      value: dashboardData.totalProducts.toString(),
      icon: ShoppingBagIcon,
      change: '+3',
      changeType: 'positive',
    },
    {
      name: 'Total Orders',
      value: dashboardData.totalOrders.toString(),
      icon: ChartBarIcon,
      change: '+15%',
      changeType: 'positive',
    },
    {
      name: 'Total Customers',
      value: dashboardData.totalCustomers.toString(),
      icon: UsersIcon,
      change: '+8%',
      changeType: 'positive',
    },
  ];

  return (
    <Layout>
      <Head>
        <title>Dashboard - Hayu Widyas Admin</title>
        <meta name="description" content="Hayu Widyas admin dashboard overview" />
      </Head>

      <div className="space-y-6">
        {/* Welcome Header */}
        <div className="bg-white shadow-elegant rounded-lg p-6">
          <div className="md:flex md:items-center md:justify-between">
            <div className="min-w-0 flex-1">
              <h2 className="text-2xl font-bold leading-7 text-neutral-900 sm:truncate sm:text-3xl sm:tracking-tight">
                Welcome back, Admin
              </h2>
              <div className="mt-1 flex flex-col sm:mt-0 sm:flex-row sm:flex-wrap sm:space-x-6">
                <div className="mt-2 flex items-center text-sm text-neutral-500">
                  <CurrencyDollarIcon className="mr-1.5 h-5 w-5 flex-shrink-0 text-neutral-400" />
                  Weekly Sales: Rp {dashboardData.weeklySales.toLocaleString()}
                </div>
                <div className="mt-2 flex items-center text-sm text-neutral-500">
                  <ChartBarIcon className="mr-1.5 h-5 w-5 flex-shrink-0 text-neutral-400" />
                  Monthly Sales: Rp {dashboardData.monthlySales.toLocaleString()}
                </div>
              </div>
            </div>
            <div className="mt-4 flex md:ml-4 md:mt-0">
              <button
                type="button"
                onClick={loadDashboardData}
                disabled={isLoading}
                className="inline-flex items-center rounded-md bg-primary-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-primary-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-primary-600 disabled:opacity-50"
              >
                {isLoading ? 'Refreshing...' : 'Refresh Data'}
              </button>
            </div>
          </div>
        </div>

        {/* Stats Grid */}
        <DashboardStats stats={stats} isLoading={isLoading} />

        {/* Charts and Tables Grid */}
        <div className="grid grid-cols-1 gap-6 lg:grid-cols-2">
          {/* Sales Chart */}
          <div className="bg-white shadow-elegant rounded-lg p-6">
            <h3 className="text-lg font-semibold text-neutral-900 mb-4">Sales Overview</h3>
            <SalesChart data={dashboardData.salesChartData} isLoading={isLoading} />
          </div>

          {/* Top Products */}
          <div className="bg-white shadow-elegant rounded-lg p-6">
            <h3 className="text-lg font-semibold text-neutral-900 mb-4">Top Products</h3>
            <TopProducts products={dashboardData.topProducts} isLoading={isLoading} />
          </div>
        </div>

        {/* Recent Orders */}
        <div className="bg-white shadow-elegant rounded-lg">
          <div className="px-6 py-4 border-b border-neutral-200">
            <h3 className="text-lg font-semibold text-neutral-900">Recent Orders</h3>
          </div>
          <RecentOrders orders={dashboardData.recentOrders} isLoading={isLoading} />
        </div>
      </div>
    </Layout>
  );
}
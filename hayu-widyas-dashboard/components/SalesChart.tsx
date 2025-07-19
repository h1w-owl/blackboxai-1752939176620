import { FC } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, AreaChart, Area } from 'recharts';

interface SalesData {
  date: string;
  sales: number;
  orders: number;
}

interface SalesChartProps {
  data: SalesData[];
  isLoading: boolean;
}

const SalesChart: FC<SalesChartProps> = ({ data, isLoading }) => {
  if (isLoading) {
    return (
      <div className="h-80 flex items-center justify-center">
        <div className="animate-pulse space-y-4 w-full">
          <div className="h-4 bg-neutral-200 rounded w-1/4"></div>
          <div className="space-y-2">
            <div className="h-4 bg-neutral-200 rounded"></div>
            <div className="h-4 bg-neutral-200 rounded w-5/6"></div>
            <div className="h-4 bg-neutral-200 rounded w-4/6"></div>
            <div className="h-4 bg-neutral-200 rounded w-3/6"></div>
            <div className="h-4 bg-neutral-200 rounded w-2/6"></div>
          </div>
        </div>
      </div>
    );
  }

  if (!data || data.length === 0) {
    return (
      <div className="h-80 flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 mx-auto mb-4 text-neutral-400">
            <svg fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
            </svg>
          </div>
          <p className="text-neutral-500 text-sm">No sales data available</p>
        </div>
      </div>
    );
  }

  const maxSales = Math.max(...data.map(d => d.sales));
  const formatCurrency = (value: number) => `Rp ${value.toLocaleString()}`;

  return (
    <div className="h-80">
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart
          data={data}
          margin={{
            top: 10,
            right: 30,
            left: 0,
            bottom: 0,
          }}
        >
          <defs>
            <linearGradient id="salesGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#f17e44" stopOpacity={0.3}/>
              <stop offset="95%" stopColor="#f17e44" stopOpacity={0}/>
            </linearGradient>
          </defs>
          <CartesianGrid strokeDasharray="3 3" stroke="#e5e7eb" />
          <XAxis 
            dataKey="date" 
            stroke="#6b7280"
            fontSize={12}
            tickLine={false}
            axisLine={false}
          />
          <YAxis 
            stroke="#6b7280"
            fontSize={12}
            tickLine={false}
            axisLine={false}
            tickFormatter={(value) => value > 999999 ? `${(value/1000000).toFixed(1)}M` : value > 999 ? `${(value/1000).toFixed(0)}K` : value.toString()}
          />
          <Tooltip 
            contentStyle={{
              backgroundColor: 'white',
              border: '1px solid #e5e7eb',
              borderRadius: '8px',
              boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
            }}
            formatter={(value: number, name: string) => [
              name === 'sales' ? formatCurrency(value) : value,
              name === 'sales' ? 'Sales' : 'Orders'
            ]}
            labelStyle={{ color: '#374151', fontWeight: 'semibold' }}
          />
          <Area
            type="monotone"
            dataKey="sales"
            stroke="#f17e44"
            strokeWidth={2}
            fill="url(#salesGradient)"
            dot={{ fill: '#f17e44', strokeWidth: 2, r: 4 }}
            activeDot={{ r: 6, stroke: '#f17e44', strokeWidth: 2, fill: 'white' }}
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  );
};

export default SalesChart;
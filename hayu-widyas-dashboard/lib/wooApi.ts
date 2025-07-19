import axios, { AxiosInstance, AxiosResponse } from 'axios';

// WooCommerce API client configuration
const createWooCommerceClient = (): AxiosInstance => {
  const client = axios.create({
    baseURL: process.env.WOOCOMMERCE_BASE_URL,
    auth: {
      username: process.env.WOOCOMMERCE_CONSUMER_KEY!,
      password: process.env.WOOCOMMERCE_CONSUMER_SECRET!,
    },
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    timeout: 30000,
  });

  // Request interceptor
  client.interceptors.request.use(
    (config) => {
      console.log(`WooCommerce API Request: ${config.method?.toUpperCase()} ${config.url}`);
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  // Response interceptor
  client.interceptors.response.use(
    (response) => {
      return response;
    },
    (error) => {
      console.error('WooCommerce API Error:', {
        message: error.message,
        status: error.response?.status,
        data: error.response?.data,
      });
      return Promise.reject(error);
    }
  );

  return client;
};

// Create WooCommerce API client instance
export const wooApi = createWooCommerceClient();

// API Response types
export interface WooProduct {
  id: number;
  name: string;
  slug: string;
  permalink: string;
  date_created: string;
  date_modified: string;
  type: string;
  status: string;
  featured: boolean;
  catalog_visibility: string;
  description: string;
  short_description: string;
  sku: string;
  price: string;
  regular_price: string;
  sale_price: string;
  on_sale: boolean;
  purchasable: boolean;
  total_sales: number;
  virtual: boolean;
  downloadable: boolean;
  manage_stock: boolean;
  stock_quantity: number | null;
  stock_status: string;
  backorders: string;
  backorders_allowed: boolean;
  backordered: boolean;
  sold_individually: boolean;
  weight: string;
  shipping_required: boolean;
  shipping_taxable: boolean;
  reviews_allowed: boolean;
  average_rating: string;
  rating_count: number;
  parent_id: number;
  purchase_note: string;
  categories: WooCategory[];
  tags: WooTag[];
  images: WooImage[];
  attributes: WooAttribute[];
  variations: number[];
  grouped_products: number[];
  menu_order: number;
  meta_data: WooMetaData[];
}

export interface WooCategory {
  id: number;
  name: string;
  slug: string;
  parent: number;
  description: string;
  display: string;
  image: WooImage | null;
  menu_order: number;
  count: number;
}

export interface WooTag {
  id: number;
  name: string;
  slug: string;
  description: string;
  count: number;
}

export interface WooImage {
  id: number;
  date_created: string;
  date_modified: string;
  src: string;
  name: string;
  alt: string;
}

export interface WooAttribute {
  id: number;
  name: string;
  position: number;
  visible: boolean;
  variation: boolean;
  options: string[];
}

export interface WooMetaData {
  id: number;
  key: string;
  value: string;
}

export interface WooOrder {
  id: number;
  parent_id: number;
  status: string;
  currency: string;
  date_created: string;
  date_modified: string;
  discount_total: string;
  discount_tax: string;
  shipping_total: string;
  shipping_tax: string;
  cart_tax: string;
  total: string;
  total_tax: string;
  customer_id: number;
  order_key: string;
  billing: WooBilling;
  shipping: WooShipping;
  payment_method: string;
  payment_method_title: string;
  transaction_id: string;
  customer_ip_address: string;
  customer_user_agent: string;
  created_via: string;
  customer_note: string;
  date_completed: string | null;
  date_paid: string | null;
  cart_hash: string;
  number: string;
  line_items: WooLineItem[];
  tax_lines: WooTaxLine[];
  shipping_lines: WooShippingLine[];
  fee_lines: WooFeeLine[];
  coupon_lines: WooCouponLine[];
  refunds: WooRefund[];
}

export interface WooBilling {
  first_name: string;
  last_name: string;
  company: string;
  address_1: string;
  address_2: string;
  city: string;
  state: string;
  postcode: string;
  country: string;
  email: string;
  phone: string;
}

export interface WooShipping {
  first_name: string;
  last_name: string;
  company: string;
  address_1: string;
  address_2: string;
  city: string;
  state: string;
  postcode: string;
  country: string;
}

export interface WooLineItem {
  id: number;
  name: string;
  product_id: number;
  variation_id: number;
  quantity: number;
  tax_class: string;
  subtotal: string;
  subtotal_tax: string;
  total: string;
  total_tax: string;
  taxes: WooTax[];
  meta_data: WooMetaData[];
  sku: string;
  price: string;
  image: WooImage;
  parent_name: string | null;
}

export interface WooTax {
  id: number;
  total: string;
  subtotal: string;
}

export interface WooTaxLine {
  id: number;
  rate_code: string;
  rate_id: number;
  label: string;
  compound: boolean;
  tax_total: string;
  shipping_tax_total: string;
  rate_percent: number;
  meta_data: WooMetaData[];
}

export interface WooShippingLine {
  id: number;
  method_title: string;
  method_id: string;
  instance_id: string;
  total: string;
  total_tax: string;
  taxes: WooTax[];
  meta_data: WooMetaData[];
}

export interface WooFeeLine {
  id: number;
  name: string;
  tax_class: string;
  tax_status: string;
  total: string;
  total_tax: string;
  taxes: WooTax[];
  meta_data: WooMetaData[];
}

export interface WooCouponLine {
  id: number;
  code: string;
  discount: string;
  discount_tax: string;
  meta_data: WooMetaData[];
}

export interface WooRefund {
  id: number;
  date_created: string;
  amount: string;
  reason: string;
  refunded_by: number;
  meta_data: WooMetaData[];
}

export interface WooCustomer {
  id: number;
  date_created: string;
  date_modified: string;
  email: string;
  first_name: string;
  last_name: string;
  role: string;
  username: string;
  billing: WooBilling;
  shipping: WooShipping;
  is_paying_customer: boolean;
  avatar_url: string;
  meta_data: WooMetaData[];
}

// API Service Class
export class WooCommerceService {
  private api: AxiosInstance;

  constructor() {
    this.api = wooApi;
  }

  // Products
  async getProducts(params?: {
    page?: number;
    per_page?: number;
    search?: string;
    category?: string;
    status?: string;
    featured?: boolean;
    orderby?: string;
    order?: 'asc' | 'desc';
  }): Promise<WooProduct[]> {
    const response: AxiosResponse<WooProduct[]> = await this.api.get('/products', { params });
    return response.data;
  }

  async getProduct(id: number): Promise<WooProduct> {
    const response: AxiosResponse<WooProduct> = await this.api.get(`/products/${id}`);
    return response.data;
  }

  async createProduct(product: Partial<WooProduct>): Promise<WooProduct> {
    const response: AxiosResponse<WooProduct> = await this.api.post('/products', product);
    return response.data;
  }

  async updateProduct(id: number, product: Partial<WooProduct>): Promise<WooProduct> {
    const response: AxiosResponse<WooProduct> = await this.api.put(`/products/${id}`, product);
    return response.data;
  }

  async deleteProduct(id: number): Promise<WooProduct> {
    const response: AxiosResponse<WooProduct> = await this.api.delete(`/products/${id}`, {
      params: { force: true }
    });
    return response.data;
  }

  // Orders
  async getOrders(params?: {
    page?: number;
    per_page?: number;
    status?: string;
    customer?: number;
    orderby?: string;
    order?: 'asc' | 'desc';
    after?: string;
    before?: string;
  }): Promise<WooOrder[]> {
    const response: AxiosResponse<WooOrder[]> = await this.api.get('/orders', { params });
    return response.data;
  }

  async getOrder(id: number): Promise<WooOrder> {
    const response: AxiosResponse<WooOrder> = await this.api.get(`/orders/${id}`);
    return response.data;
  }

  async updateOrder(id: number, order: Partial<WooOrder>): Promise<WooOrder> {
    const response: AxiosResponse<WooOrder> = await this.api.put(`/orders/${id}`, order);
    return response.data;
  }

  // Customers
  async getCustomers(params?: {
    page?: number;
    per_page?: number;
    search?: string;
    orderby?: string;
    order?: 'asc' | 'desc';
    role?: string;
  }): Promise<WooCustomer[]> {
    const response: AxiosResponse<WooCustomer[]> = await this.api.get('/customers', { params });
    return response.data;
  }

  async getCustomer(id: number): Promise<WooCustomer> {
    const response: AxiosResponse<WooCustomer> = await this.api.get(`/customers/${id}`);
    return response.data;
  }

  // Categories
  async getCategories(params?: {
    page?: number;
    per_page?: number;
    search?: string;
    orderby?: string;
    order?: 'asc' | 'desc';
    hide_empty?: boolean;
  }): Promise<WooCategory[]> {
    const response: AxiosResponse<WooCategory[]> = await this.api.get('/products/categories', { params });
    return response.data;
  }

  // Reports
  async getSalesReport(params?: {
    period?: string;
    date_min?: string;
    date_max?: string;
  }) {
    const response = await this.api.get('/reports/sales', { params });
    return response.data;
  }

  async getTopSellers(params?: {
    period?: string;
    date_min?: string;
    date_max?: string;
  }) {
    const response = await this.api.get('/reports/top_sellers', { params });
    return response.data;
  }
}

// Export singleton instance
export const wooService = new WooCommerceService();
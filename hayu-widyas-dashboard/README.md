# Hayu Widyas - Admin Dashboard

Professional web-based admin dashboard for managing the Hayu Widyas luxury leather bag e-commerce platform. Built with Next.js, TypeScript, and modern web technologies.

## 🎯 Overview

This dashboard provides comprehensive management capabilities for:
- **Product Management**: CRUD operations for luxury leather bags
- **Order Management**: Process and track customer orders
- **Customer Management**: View and manage customer data
- **Sales Analytics**: Real-time sales reporting and charts
- **Content Management**: Manage website content and blog posts
- **Multi-language Support**: English and Indonesian localization
- **Responsive Design**: Works seamlessly on desktop and mobile

## 🏗️ Architecture

```
🌐 Frontend (Next.js + TypeScript)
├── Pages (App Router)
├── Components (Reusable UI)
├── Hooks (Custom React hooks)
└── Utils (Helper functions)

🔄 API Layer
├── WooCommerce REST API
├── Firebase Admin SDK
└── Custom API endpoints

💾 Data Sources
├── WooCommerce (Products, Orders, Customers)
├── Firebase (Authentication, Storage)
└── Custom Backend Services
```

## 🛠️ Tech Stack

### Frontend
- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS + Shadcn/ui
- **State Management**: React Hook Form + SWR
- **Charts**: Recharts
- **UI Components**: Radix UI + Headless UI
- **Icons**: Heroicons + Lucide React

### Backend & Services
- **Authentication**: Firebase Auth
- **Database**: WooCommerce + Firebase Firestore
- **File Storage**: Firebase Storage
- **API**: WooCommerce REST API
- **Validation**: Zod
- **HTTP Client**: Axios

### Development
- **Package Manager**: npm/yarn
- **Code Quality**: ESLint + Prettier
- **Type Checking**: TypeScript strict mode

## 🚀 Quick Start

### Prerequisites
- Node.js 18+ 
- npm or yarn
- Firebase project
- WooCommerce store with REST API enabled

### 1. Clone & Install
```bash
git clone https://github.com/your-username/hayu-widyas.git
cd hayu-widyas/hayu-widyas-dashboard
npm install
```

### 2. Environment Setup
Copy `.env.example` to `.env.local` and configure:

```bash
cp .env.example .env.local
```

Edit `.env.local` with your credentials:

```env
# Firebase Configuration
NEXT_PUBLIC_FIREBASE_API_KEY=your_api_key
NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=project.firebaseapp.com
NEXT_PUBLIC_FIREBASE_PROJECT_ID=your_project_id
NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=project.appspot.com
NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID=123456789
NEXT_PUBLIC_FIREBASE_APP_ID=1:123456789:web:abcdef

# Firebase Admin SDK (Server-side)
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\nYOUR_PRIVATE_KEY\n-----END PRIVATE KEY-----\n"
FIREBASE_CLIENT_EMAIL=firebase-adminsdk-xxx@project.iam.gserviceaccount.com
FIREBASE_PROJECT_ID=your_project_id

# WooCommerce API
WOOCOMMERCE_BASE_URL=https://hayuwidyas.com/wp-json/wc/v3/
WOOCOMMERCE_CONSUMER_KEY=ck_your_consumer_key
WOOCOMMERCE_CONSUMER_SECRET=cs_your_consumer_secret

# Admin Access
ADMIN_EMAIL=admin@hayuwidyas.com
ADMIN_PASSWORD=secure_password_here
```

### 3. Firebase Setup

1. **Create Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create new project
   - Enable Authentication, Firestore, Storage

2. **Configure Authentication**
   - Enable Email/Password provider
   - Add admin user manually

3. **Generate Service Account Key**
   - Go to Project Settings → Service Accounts
   - Generate new private key
   - Use credentials in `.env.local`

### 4. WooCommerce Setup

1. **Enable REST API**
   ```
   WordPress Admin → WooCommerce → Settings → Advanced → REST API
   ```

2. **Create API Keys**
   - Generate new key with Read/Write permissions
   - Add consumer key/secret to `.env.local`

3. **Configure CORS** (if needed)
   ```php
   // In WordPress functions.php or plugin
   add_action('rest_api_init', function() {
       remove_filter('rest_pre_serve_request', 'rest_send_cors_headers');
       add_filter('rest_pre_serve_request', function($value) {
           header('Access-Control-Allow-Origin: *');
           header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
           header('Access-Control-Allow-Headers: Authorization, Content-Type');
           return $value;
       });
   });
   ```

### 5. Run Development Server

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

## 📂 Project Structure

```
hayu-widyas-dashboard/
├── pages/                       # Next.js pages
│   ├── index.tsx               # Login page
│   ├── dashboard/              # Dashboard pages
│   │   ├── index.tsx          # Dashboard home
│   │   ├── products.tsx       # Product management
│   │   ├── orders.tsx         # Order management
│   │   ├── customers.tsx      # Customer management
│   │   └── settings.tsx       # Settings
│   └── api/                    # API routes
├── components/                  # Reusable components
│   ├── Layout.tsx              # Main layout wrapper
│   ├── Sidebar.tsx             # Navigation sidebar
│   ├── Header.tsx              # Top header
│   ├── DashboardStats.tsx      # Stats cards
│   ├── SalesChart.tsx          # Sales analytics
│   └── ui/                     # UI components
├── lib/                        # Utility libraries
│   ├── firebase.ts             # Firebase client config
│   ├── firebase-admin.ts       # Firebase admin config
│   ├── wooApi.ts              # WooCommerce API client
│   └── utils.ts               # Helper functions
├── styles/                     # Styling
│   └── globals.css            # Global styles
├── public/                     # Static assets
│   └── images/                # Images and icons
├── types/                      # TypeScript types
├── hooks/                      # Custom React hooks
├── .env.example               # Environment variables template
├── next.config.js             # Next.js configuration
├── tailwind.config.js         # Tailwind CSS configuration
├── tsconfig.json              # TypeScript configuration
└── package.json               # Dependencies and scripts
```

## 🎨 Features

### Dashboard Overview
- **Sales Metrics**: Today, week, month sales with trends
- **Quick Stats**: Products, orders, customers count
- **Sales Charts**: Visual analytics with Recharts
- **Recent Orders**: Latest order status and details
- **Top Products**: Best-selling products list

### Product Management
- **Product CRUD**: Create, read, update, delete products
- **Image Upload**: Firebase Storage integration
- **Inventory**: Stock management and tracking
- **Categories**: Product categorization
- **Bulk Actions**: Mass product operations

### Order Management
- **Order List**: Filterable and sortable orders
- **Order Details**: Complete order information
- **Status Updates**: Change order status
- **Customer Info**: Order-related customer data
- **Export**: Order data export functionality

### Customer Management
- **Customer Database**: Complete customer profiles
- **Order History**: Customer purchase history
- **Analytics**: Customer lifetime value
- **Communication**: Customer contact management

### Content Management
- **Blog Posts**: Create and manage blog content
- **Pages**: Static page management
- **Media**: Image and file management
- **SEO**: Meta tags and optimization

### Analytics & Reporting
- **Sales Reports**: Detailed sales analytics
- **Product Performance**: Best/worst selling products
- **Customer Analytics**: Customer behavior insights
- **Export Data**: CSV/Excel export functionality

## 🔧 Configuration

### Tailwind CSS Customization
The dashboard uses a custom Tailwind configuration with luxury brand colors:

```javascript
// tailwind.config.js
module.exports = {
  theme: {
    extend: {
      colors: {
        primary: { /* Luxury orange tones */ },
        secondary: { /* Warm brown tones */ },
        accent: { /* Modern blue accents */ },
        neutral: { /* Sophisticated grays */ },
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
        display: ['Playfair Display', 'serif'],
      },
    },
  },
}
```

### API Rate Limiting
WooCommerce API has rate limits. The dashboard implements:
- Request queuing
- Retry mechanisms
- Error handling
- Caching strategies

## 🚀 Deployment

### Vercel (Recommended)
```bash
# Install Vercel CLI
npm i -g vercel

# Deploy
vercel

# Set environment variables in Vercel dashboard
```

### Other Platforms
The dashboard can be deployed to:
- Netlify
- Railway
- DigitalOcean App Platform
- AWS Amplify
- Custom VPS with PM2

### Build for Production
```bash
npm run build
npm start
```

## 🧪 Development

### Scripts
```bash
npm run dev          # Start development server
npm run build        # Build for production
npm run start        # Start production server
npm run lint         # Run ESLint
npm run type-check   # TypeScript type checking
```

### Code Quality
```bash
# Format code with Prettier
npm run format

# Lint and fix
npm run lint:fix

# Type check
npm run type-check
```

## 🔒 Security

### Authentication
- Firebase Authentication with admin-only access
- Protected routes with middleware
- Session management

### API Security
- Environment variable protection
- Request validation with Zod
- Rate limiting and CORS

### Data Protection
- Input sanitization
- XSS prevention
- CSRF protection

## 📊 Performance

### Optimization Features
- Next.js Image optimization
- Static generation where possible
- Dynamic imports for code splitting
- SWR for efficient data fetching
- Compression and minification

### Monitoring
- Error tracking with Sentry (optional)
- Performance monitoring
- Analytics integration

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -m 'Add new feature'`
4. Push to branch: `git push origin feature/new-feature`
5. Submit Pull Request

### Code Style
- Use TypeScript strict mode
- Follow ESLint configuration
- Use Prettier for formatting
- Write meaningful commit messages

## 🆘 Troubleshooting

### Common Issues

1. **Firebase connection fails**
   ```bash
   # Check environment variables
   # Verify Firebase project settings
   # Ensure service account key is correct
   ```

2. **WooCommerce API errors**
   ```bash
   # Verify API credentials
   # Check WooCommerce REST API is enabled
   # Ensure proper CORS configuration
   ```

3. **Build errors**
   ```bash
   # Clear Next.js cache
   rm -rf .next
   npm run build
   ```

### Environment Issues
- Ensure all required environment variables are set
- Check Firebase project configuration
- Verify WooCommerce store accessibility

## 📄 License

Copyright © 2024 Hayu Widyas. All rights reserved.

## 🔗 Related Projects

- [Hayu Widyas Android App](../hayu-widyas-app/) - Customer mobile app
- [Hayu Widyas Website](https://hayuwidyas.com) - Official e-commerce website

---

**Built with precision for luxury brand management** ✨
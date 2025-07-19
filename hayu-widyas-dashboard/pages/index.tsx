import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Head from 'next/head';
import Image from 'next/image';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from '../lib/firebase';
import { toast, Toaster } from 'react-hot-toast';
import { EyeIcon, EyeSlashIcon } from '@heroicons/react/24/outline';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);
  const router = useRouter();

  useEffect(() => {
    // Check if user is already logged in
    const unsubscribe = auth.onAuthStateChanged((user) => {
      if (user) {
        router.push('/dashboard');
      }
    });

    return () => unsubscribe();
  }, [router]);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      await signInWithEmailAndPassword(auth, email, password);
      toast.success('Login successful!');
      router.push('/dashboard');
    } catch (error: any) {
      console.error('Login error:', error);
      toast.error(error.message || 'Login failed. Please check your credentials.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <Head>
        <title>Admin Login - Hayu Widyas Dashboard</title>
        <meta name="description" content="Admin login for Hayu Widyas luxury leather bag management dashboard" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <div className="min-h-screen bg-gradient-to-br from-primary-50 to-secondary-100 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
        <Toaster position="top-right" />
        
        <div className="sm:mx-auto sm:w-full sm:max-w-md">
          <div className="flex justify-center">
            <div className="w-20 h-20 bg-primary-600 rounded-full flex items-center justify-center shadow-luxury">
              <span className="text-white text-2xl font-display font-bold">HW</span>
            </div>
          </div>
          <h2 className="mt-6 text-center text-3xl font-display font-bold text-neutral-900">
            Hayu Widyas
          </h2>
          <p className="mt-2 text-center text-sm text-neutral-600">
            Admin Dashboard
          </p>
        </div>

        <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
          <div className="bg-white py-8 px-4 shadow-luxury sm:rounded-lg sm:px-10">
            <form className="space-y-6" onSubmit={handleLogin}>
              <div>
                <label htmlFor="email" className="block text-sm font-medium text-neutral-700">
                  Email address
                </label>
                <div className="mt-1">
                  <input
                    id="email"
                    name="email"
                    type="email"
                    autoComplete="email"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="appearance-none block w-full px-3 py-2 border border-neutral-300 rounded-md placeholder-neutral-400 focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
                    placeholder="admin@hayuwidyas.com"
                  />
                </div>
              </div>

              <div>
                <label htmlFor="password" className="block text-sm font-medium text-neutral-700">
                  Password
                </label>
                <div className="mt-1 relative">
                  <input
                    id="password"
                    name="password"
                    type={showPassword ? 'text' : 'password'}
                    autoComplete="current-password"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="appearance-none block w-full px-3 py-2 pr-10 border border-neutral-300 rounded-md placeholder-neutral-400 focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
                    placeholder="Enter your password"
                  />
                  <button
                    type="button"
                    className="absolute inset-y-0 right-0 pr-3 flex items-center"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? (
                      <EyeSlashIcon className="h-5 w-5 text-neutral-400" />
                    ) : (
                      <EyeIcon className="h-5 w-5 text-neutral-400" />
                    )}
                  </button>
                </div>
              </div>

              <div className="flex items-center justify-between">
                <div className="flex items-center">
                  <input
                    id="remember-me"
                    name="remember-me"
                    type="checkbox"
                    checked={rememberMe}
                    onChange={(e) => setRememberMe(e.target.checked)}
                    className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-neutral-300 rounded"
                  />
                  <label htmlFor="remember-me" className="ml-2 block text-sm text-neutral-900">
                    Remember me
                  </label>
                </div>

                <div className="text-sm">
                  <a href="#" className="font-medium text-primary-600 hover:text-primary-500">
                    Forgot your password?
                  </a>
                </div>
              </div>

              <div>
                <button
                  type="submit"
                  disabled={isLoading}
                  className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-primary-600 hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors duration-200"
                >
                  {isLoading ? (
                    <div className="flex items-center">
                      <div className="animate-spin -ml-1 mr-3 h-5 w-5 text-white">
                        <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24">
                          <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                          <path className="opacity-75" fill="currentColor" d="m4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                        </svg>
                      </div>
                      Signing in...
                    </div>
                  ) : (
                    'Sign in'
                  )}
                </button>
              </div>
            </form>

            <div className="mt-6">
              <div className="relative">
                <div className="absolute inset-0 flex items-center">
                  <div className="w-full border-t border-neutral-300" />
                </div>
                <div className="relative flex justify-center text-sm">
                  <span className="px-2 bg-white text-neutral-500">Admin Access Only</span>
                </div>
              </div>
            </div>

            <div className="mt-6 text-center">
              <p className="text-xs text-neutral-500">
                © 2024 Hayu Widyas. All rights reserved.
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
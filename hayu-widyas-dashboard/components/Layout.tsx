import { useState, ReactNode } from 'react';
import { useRouter } from 'next/router';
import { signOut } from 'firebase/auth';
import { auth } from '../lib/firebase';
import { toast } from 'react-hot-toast';
import {
  HomeIcon,
  ShoppingBagIcon,
  ClipboardDocumentListIcon,
  UsersIcon,
  DocumentTextIcon,
  Cog6ToothIcon,
  ArrowRightOnRectangleIcon,
  Bars3Icon,
  XMarkIcon,
} from '@heroicons/react/24/outline';
import { Fragment } from 'react';
import { Dialog, Transition } from '@headlessui/react';

interface LayoutProps {
  children: ReactNode;
}

const navigation = [
  { name: 'Dashboard', href: '/dashboard', icon: HomeIcon },
  { name: 'Products', href: '/dashboard/products', icon: ShoppingBagIcon },
  { name: 'Orders', href: '/dashboard/orders', icon: ClipboardDocumentListIcon },
  { name: 'Customers', href: '/dashboard/customers', icon: UsersIcon },
  { name: 'Content', href: '/dashboard/content', icon: DocumentTextIcon },
  { name: 'Settings', href: '/dashboard/settings', icon: Cog6ToothIcon },
];

export default function Layout({ children }: LayoutProps) {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const router = useRouter();

  const handleSignOut = async () => {
    try {
      await signOut(auth);
      toast.success('Signed out successfully');
      router.push('/');
    } catch (error) {
      console.error('Sign out error:', error);
      toast.error('Failed to sign out');
    }
  };

  return (
    <>
      <div>
        <Transition.Root show={sidebarOpen} as={Fragment}>
          <Dialog as="div" className="relative z-50 lg:hidden" onClose={setSidebarOpen}>
            <Transition.Child
              as={Fragment}
              enter="transition-opacity ease-linear duration-300"
              enterFrom="opacity-0"
              enterTo="opacity-100"
              leave="transition-opacity ease-linear duration-300"
              leaveFrom="opacity-100"
              leaveTo="opacity-0"
            >
              <div className="fixed inset-0 bg-neutral-900/80" />
            </Transition.Child>

            <div className="fixed inset-0 flex">
              <Transition.Child
                as={Fragment}
                enter="transition ease-in-out duration-300 transform"
                enterFrom="-translate-x-full"
                enterTo="translate-x-0"
                leave="transition ease-in-out duration-300 transform"
                leaveFrom="translate-x-0"
                leaveTo="-translate-x-full"
              >
                <Dialog.Panel className="relative mr-16 flex w-full max-w-xs flex-1">
                  <Transition.Child
                    as={Fragment}
                    enter="ease-in-out duration-300"
                    enterFrom="opacity-0"
                    enterTo="opacity-100"
                    leave="ease-in-out duration-300"
                    leaveFrom="opacity-100"
                    leaveTo="opacity-0"
                  >
                    <div className="absolute left-full top-0 flex w-16 justify-center pt-5">
                      <button
                        type="button"
                        className="-m-2.5 p-2.5"
                        onClick={() => setSidebarOpen(false)}
                      >
                        <span className="sr-only">Close sidebar</span>
                        <XMarkIcon className="h-6 w-6 text-white" aria-hidden="true" />
                      </button>
                    </div>
                  </Transition.Child>
                  <div className="flex grow flex-col gap-y-5 overflow-y-auto bg-white px-6 pb-2">
                    <div className="flex h-16 shrink-0 items-center">
                      <div className="w-8 h-8 bg-primary-600 rounded-full flex items-center justify-center">
                        <span className="text-white text-sm font-display font-bold">HW</span>
                      </div>
                      <span className="ml-2 text-xl font-display font-bold text-neutral-900">
                        Hayu Widyas
                      </span>
                    </div>
                    <nav className="flex flex-1 flex-col">
                      <ul role="list" className="flex flex-1 flex-col gap-y-7">
                        <li>
                          <ul role="list" className="-mx-2 space-y-1">
                            {navigation.map((item) => (
                              <li key={item.name}>
                                <a
                                  href={item.href}
                                  className={`${
                                    router.pathname === item.href
                                      ? 'bg-primary-50 text-primary-600'
                                      : 'text-neutral-700 hover:text-primary-600 hover:bg-neutral-50'
                                  } group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold`}
                                >
                                  <item.icon
                                    className={`${
                                      router.pathname === item.href
                                        ? 'text-primary-600'
                                        : 'text-neutral-400 group-hover:text-primary-600'
                                    } h-6 w-6 shrink-0`}
                                    aria-hidden="true"
                                  />
                                  {item.name}
                                </a>
                              </li>
                            ))}
                          </ul>
                        </li>
                        <li className="-mx-6 mt-auto">
                          <button
                            onClick={handleSignOut}
                            className="flex items-center gap-x-4 px-6 py-3 text-sm font-semibold leading-6 text-neutral-900 hover:bg-neutral-50 w-full text-left"
                          >
                            <ArrowRightOnRectangleIcon
                              className="h-6 w-6 text-neutral-400"
                              aria-hidden="true"
                            />
                            Sign out
                          </button>
                        </li>
                      </ul>
                    </nav>
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </Dialog>
        </Transition.Root>

        {/* Static sidebar for desktop */}
        <div className="hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-72 lg:flex-col">
          <div className="flex grow flex-col gap-y-5 overflow-y-auto border-r border-neutral-200 bg-white px-6">
            <div className="flex h-16 shrink-0 items-center">
              <div className="w-8 h-8 bg-primary-600 rounded-full flex items-center justify-center">
                <span className="text-white text-sm font-display font-bold">HW</span>
              </div>
              <span className="ml-2 text-xl font-display font-bold text-neutral-900">
                Hayu Widyas
              </span>
            </div>
            <nav className="flex flex-1 flex-col">
              <ul role="list" className="flex flex-1 flex-col gap-y-7">
                <li>
                  <ul role="list" className="-mx-2 space-y-1">
                    {navigation.map((item) => (
                      <li key={item.name}>
                        <a
                          href={item.href}
                          className={`${
                            router.pathname === item.href
                              ? 'bg-primary-50 text-primary-600'
                              : 'text-neutral-700 hover:text-primary-600 hover:bg-neutral-50'
                          } group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold transition-colors duration-200`}
                        >
                          <item.icon
                            className={`${
                              router.pathname === item.href
                                ? 'text-primary-600'
                                : 'text-neutral-400 group-hover:text-primary-600'
                            } h-6 w-6 shrink-0`}
                            aria-hidden="true"
                          />
                          {item.name}
                        </a>
                      </li>
                    ))}
                  </ul>
                </li>
                <li className="-mx-6 mt-auto">
                  <button
                    onClick={handleSignOut}
                    className="flex items-center gap-x-4 px-6 py-3 text-sm font-semibold leading-6 text-neutral-900 hover:bg-neutral-50 w-full text-left transition-colors duration-200"
                  >
                    <ArrowRightOnRectangleIcon
                      className="h-6 w-6 text-neutral-400"
                      aria-hidden="true"
                    />
                    Sign out
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        </div>

        <div className="lg:pl-72">
          <div className="sticky top-0 z-40 flex h-16 shrink-0 items-center gap-x-4 border-b border-neutral-200 bg-white px-4 shadow-sm sm:gap-x-6 sm:px-6 lg:px-8">
            <button
              type="button"
              className="-m-2.5 p-2.5 text-neutral-700 lg:hidden"
              onClick={() => setSidebarOpen(true)}
            >
              <span className="sr-only">Open sidebar</span>
              <Bars3Icon className="h-6 w-6" aria-hidden="true" />
            </button>

            <div className="flex flex-1 gap-x-4 self-stretch lg:gap-x-6">
              <div className="flex flex-1 items-center">
                <h1 className="text-lg font-semibold text-neutral-900">
                  {navigation.find(item => item.href === router.pathname)?.name || 'Dashboard'}
                </h1>
              </div>
              <div className="flex items-center gap-x-4 lg:gap-x-6">
                <div className="hidden lg:block lg:h-6 lg:w-px lg:bg-neutral-200" />
                <div className="flex items-center gap-x-2">
                  <span className="text-sm text-neutral-500">Admin</span>
                  <div className="w-8 h-8 bg-primary-600 rounded-full flex items-center justify-center">
                    <span className="text-white text-xs font-semibold">A</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <main className="py-6">
            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
              {children}
            </div>
          </main>
        </div>
      </div>
    </>
  );
}
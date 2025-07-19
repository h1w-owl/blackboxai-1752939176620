import { initializeApp, getApps, cert, ServiceAccount } from 'firebase-admin/app';
import { getAuth as getAdminAuth } from 'firebase-admin/auth';
import { getFirestore as getAdminFirestore } from 'firebase-admin/firestore';
import { getStorage as getAdminStorage } from 'firebase-admin/storage';

// Firebase Admin SDK configuration
const serviceAccount: ServiceAccount = {
  projectId: process.env.FIREBASE_PROJECT_ID!,
  clientEmail: process.env.FIREBASE_CLIENT_EMAIL!,
  privateKey: process.env.FIREBASE_PRIVATE_KEY?.replace(/\\n/g, '\n')!,
};

// Initialize Firebase Admin (server-side)
const adminApp = getApps().length === 0 
  ? initializeApp({
      credential: cert(serviceAccount),
      storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
    }, 'admin')
  : getApps()[0];

// Initialize Firebase Admin services
export const adminAuth = getAdminAuth(adminApp);
export const adminDb = getAdminFirestore(adminApp);
export const adminStorage = getAdminStorage(adminApp);

export default adminApp;
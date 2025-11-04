import type { User } from '@/common/types/types';
import { create } from 'zustand'
import { persist, createJSONStorage } from 'zustand/middleware'

interface AuthState {
  user: User | null;
  isAuth: boolean;
  login: (userData: User) => void;
  logout: () => void;
}


export const useAuth = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      isAuth: false,

      login: (userData: User) => set({ user: userData, isAuth: true }),
      logout: () => set({ user: null, isAuth: false }),
    }),
    {
      name: 'auth-storage',
      storage: createJSONStorage(() => localStorage)
    }
  )
);

/* export const appGetUser = (): Promise<User | null> => {
  return new Promise((resolve) => {
    if (useAuth.persist.hasHydrated()) {
      resolve(useAuth.getState().user);
    } else {
      const unsubscribe = useAuth.persist.onFinishHydration(() => {
        resolve(useAuth.getState().user);
        unsubscribe();
      });
    }
  });
}; */

export const appGetUser = () => {
  const data = useAuth.getState();
  return data.user;

};


export const appSaveUser = (user: User) => {
  console.log(user);
  useAuth.getState().login(user);
}

export const appLogout = () => {
  useAuth.getState().logout();
}

export default useAuth
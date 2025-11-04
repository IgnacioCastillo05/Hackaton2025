import { appLogout } from "@/store/userStore";
import axios, { AxiosError } from "axios";
import { toast } from "sonner";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'https://todo-byte.onrender.com/',
  withCredentials: true
});

// Interceptor para hacer logout automático en caso de error 401
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error: AxiosError) => {
    if (error.response && error.response.status === 401) {
      toast.info("Tu sesión ha expirado o es inválida. Por favor, inicia sesión de nuevo.");

      appLogout()
      window.location.href = '/';
      return Promise.reject(error);
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
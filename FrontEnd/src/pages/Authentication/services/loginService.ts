import axiosInstance from "@/common/services/api";

export async function postLogin(data: { email: string; password: string }) {
  try {
    const response = await axiosInstance.post('auth/login', data)
    console.log(response.data);
  
  return response.data
  } catch (e) {
    console.log(e);
    throw new Error('Error al iniciar sesión');
  }
}

export async function postRegister(data: { email: string; password: string; nombre: string; confirmarPassword: string }) {
  console.log(data);
  
  const response = await axiosInstance.put('auth/register', data)
  return response.data
}
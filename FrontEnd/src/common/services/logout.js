import axiosInstance from "./api";
export async function logoutService() {
    const response = await axiosInstance.post('auth/logout');
    return response.data;
}

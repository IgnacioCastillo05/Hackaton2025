import axiosInstance from "@/common/services/api";
export async function postLogin(data) {
    try {
        const response = await axiosInstance.post('auth/login', data);
        console.log(response.data);
        return response.data;
    }
    catch (e) {
        console.log(e);
        throw new Error('Error al iniciar sesión');
    }
}
export async function postRegister(data) {
    console.log(data);
    const response = await axiosInstance.put('auth/register', data);
    return response.data;
}

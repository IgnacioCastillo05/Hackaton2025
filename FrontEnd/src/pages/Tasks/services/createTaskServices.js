import axiosInstance from "@/common/services/api";
export async function createTaskServices(data) {
    const response = await axiosInstance.post('/tasks', data);
    console.log(response.data);
    return response.data;
}

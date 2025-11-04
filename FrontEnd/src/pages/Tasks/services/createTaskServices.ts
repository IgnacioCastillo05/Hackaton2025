import axiosInstance from "@/common/services/api";
import type { Task } from "@/common/types/types";


export async function createTaskServices(data: Task) {
    const response = await axiosInstance.post('/tasks', data);
    console.log(response.data);
    return response.data;
}

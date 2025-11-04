import axiosInstance from "@/common/services/api";
export async function updateTaskService(id, data) {
    const response = await axiosInstance.put(`tasks/${id}`, data);
    return response.data;
}

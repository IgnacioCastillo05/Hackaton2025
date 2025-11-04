import axiosInstance from "@/common/services/api";

export async function getTasksById(id: string) {
  try {
    const response = await axiosInstance.get(`/tasks/${id}`);
    return response.data;
  } catch (e) {
    console.log(e);
    
  }
}
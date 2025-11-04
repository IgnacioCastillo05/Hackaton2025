import axiosInstance from "@/common/services/api";
import type { Task } from "@/common/types/types";

export async function updateTaskService(id: string, data: Partial<Task>) {
  const response = await axiosInstance.put(`tasks/${id}`, data);
  return response.data
}
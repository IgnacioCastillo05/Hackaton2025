import { appGetUser } from "@/store/userStore";
import axiosInstance from "./api";

export async function getCategories() {
  const data = appGetUser()
  
  try {
    const response = await axiosInstance.get('categories', {
      params: {
        id: data?.id // Assuming the user object has an 'id' property
      }
    });
    
    return response.data;
  } catch (error) {
    console.error("Error fetching categories:", error);
    return [];
    
  }
}
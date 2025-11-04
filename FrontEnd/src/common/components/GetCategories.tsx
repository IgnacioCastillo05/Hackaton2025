import { IoAdd } from "react-icons/io5";
import { NavLink } from "react-router-dom";
import { ModalCreateCategory } from "./ModalCreateCategorie";
import { useQuery } from "@tanstack/react-query";
import { getCategories } from "../services/getCategories";
import type { Category } from "../types/types";
import { FaRegTrashAlt } from "react-icons/fa";
import { ModalDeleteCat } from "./ModalDeleteCat";

export function GetCategories() {
  const { data: categories, isLoading } = useQuery({
    queryKey: ["categories"],
    queryFn: async () => getCategories(),
    staleTime: 1000 * 60 * 5
  })
  return (
    <div>
      <h2 className="font-bold text-gray-500 mb-2">Categorías</h2>
      <ul className="ml-4 space-y-1 mb-2">
        {!isLoading && categories.map((cat: Category) => (
          <NavLink
            key={cat.id}
            to={`task/${cat.id}/${cat.nombre}`}
            className={({ isActive }) =>
              `flex items-center justify-between capitalize px-5 py-2 gap-5 rounded hover:bg-gray-200 transition-colors ${isActive ? "bg-gray-200" : ""
              }`
            }
          >
            {cat.nombre} 
            <ModalDeleteCat id={cat.id}>
              <FaRegTrashAlt 
              className="text-red-500 hover:text-red-700 cursor-pointer" />
            </ModalDeleteCat>
          </NavLink>
        ))}

        {categories && categories.length === 0 && !isLoading && (
          <div className="text-gray-800 font-bold mt-5">No hay categorias</div>)
        }
      </ul>

      <ModalCreateCategory>
        <div
          className="flex flex-row items-center 
          p-2 gap-3 
          rounded 
          hover:bg-gray-200 
          transition-colors text-emerald-700
          cursor-pointer
          ">
          <IoAdd />
          Crear nueva categoría
        </div>
      </ModalCreateCategory>
    </div>
  )
}
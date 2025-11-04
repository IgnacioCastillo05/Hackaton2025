import { ModalCreateCategory } from "@/common/components/ModalCreateCategorie";
import { MdOutlineEditNote } from "react-icons/md";

export function Home() {
  return (
    <section className="w-full h-full flex flex-col items-center justify-center">

      <ModalCreateCategory>
        <div className="cursor-pointer flex flex-col items-center justify-center p-3 rounded-md border border-gray-600 hover:bg-gray-100 transition-colors">
          <MdOutlineEditNote className="text-8xl text-gray-500 mb-4" />
          <h2>Crear categoría</h2>
        </div>
      </ModalCreateCategory>

    </section>
  )
}
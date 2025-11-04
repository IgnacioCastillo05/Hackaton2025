import type { Task } from "@/common/types/types";
import { ModalUpdateTarea } from "./ModalUpdateTask";
import { useMutation } from "@tanstack/react-query";
import { updateTaskService } from "../services/updateTaskService";
import { toast } from "sonner";

const dificultColores = {
  Facil: 'bg-green-100 text-green-800 border-green-100',
  Medio: 'bg-yellow-100 text-yellow-800 border-yellow-100',
  Dificil: 'bg-red-100 text-red-800 border-red-100',
}

type TaskComponentProps = Pick<Task, 'titulo' | 'descripcion' | 'fechaLimite' | 'dificultad' | 'id' | 'completada'>;

export function TaskComponent(data: TaskComponentProps) {
  const mutation = useMutation({
    mutationFn: ({ id, data }: { id: string; data: Task }) => updateTaskService(id, data),
    onSuccess: () => {
      toast.success("Tarea actualizada");
      //queryClient.invalidateQueries({ queryKey: ["categories"] });
    },
    onError: () => {
      toast.error("Error al actualizar la tarea");
    }
  })

  const handleUpdateTask = (e: React.MouseEvent) => {
    e.stopPropagation();

    mutation.mutate({id: (data as any).id, data: { ...data, completada: !data.completada } as Task });
    
  }
  
  return (
    <ModalUpdateTarea
      data={data}
      mutate={(partialTask) => {
        mutation.mutate({ id: (data as any).id, data: { ...data, ...partialTask } as Task });
      }}
    >
      <div className="flex flex-row border-2 border-gray-200 rounded-md p-5 cursor-pointer hover:bg-gray-50 transition-colors">
        <div>
          <input
            type="checkbox"
            className="
            relative size-5 appearance-none
            rounded-full border border-gray-500 bg-white
            before:absolute before:inset-1 before:rounded-full
            before:bg-white not-checked:before:hidden
            checked:border-emerald-600
            checked:bg-emerald-600
            focus-visible:outline-2
            focus-visible:outline-offset-2
            focus-visible:outline-indigo-60
            disabled:border-gray-300
            disabled:bg-gray-100
            disabled:before:bg-gray-400
            forced-colors:appearance-auto
            cursor-pointer
            forced-colors:before:hidden" 
            onClick={handleUpdateTask}
            defaultChecked={data.completada}
          />
                      
        </div>
        <div className="flex-1 flex flex-col ml-6">
          <p className="text-gray-800 font-semibold text-lg capitalize">{data.titulo}</p>
          <p className="text-gray-600">{data.descripcion.charAt(0).toUpperCase() + data.descripcion.slice(1)}</p>
          <div className="flex flex-row justify-between items-center mt-2">
            {new Date(data.fechaLimite).toLocaleDateString('es-ES', {
              year: 'numeric',
              month: 'long',
              day: 'numeric'
            })}
            <div 
            className={`border-1 rounded-sm px-10 ${dificultColores[data.dificultad as keyof typeof dificultColores]}`}>
              {data.dificultad}</div>
          </div>
        </div>
      </div>
    </ModalUpdateTarea>
  );
}
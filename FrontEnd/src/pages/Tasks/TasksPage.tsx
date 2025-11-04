import { useParams } from "react-router-dom";
import { DialogNewTask } from "./components/ModalCreateTask";
import { getTasksById } from "./services/getTasksById";
import { DotLottieReact } from "@lottiefiles/dotlottie-react";
import type { Task } from "@/common/types/types";
import { TaskComponent } from "./components/Task";
import { FilterSection } from "./components/FilterSection";
import { useCallback, useEffect, useState } from "react";

export function TasksPage() {
  const { id, nombre } = useParams()
  const [data, setData] = useState<Task[]>();
  const [filterData, setFilterData] = useState<Task[]>();
  

  useEffect(() => {
    const fetchData = async () => {
      if (id) {
        const response = await getTasksById(id);
        setData(response);
        setFilterData(response);
      }
    };
    fetchData();
  }, [id]);
  
  const handleSortTasks = useCallback((sortedData: Task[]) => {
    setData(sortedData);
  }, []);
  return (
    <section className="h-full w-full p-6 overflow-auto relative">
      <h1 className="text-3xl font-semibold capitalize">{nombre}</h1>
      <section className="">
        <p className="text-gray-600 font-semibold p-5">
          Lista de tareas
        </p>
        <div>
            <DialogNewTask id={id as string} />
            <FilterSection data={filterData || []} onSort={(data) => handleSortTasks(data)}/>
          <div className="py-2 space-y-2 mt-3">
            {data && data.length > 0 ? (
              <>
                {data.map((task: Task) => (
                <TaskComponent
                    key={task.id}
                    id={task.id}
                    completada={task.completada}
                    titulo={task.titulo}
                    descripcion={task.descripcion}
                    fechaLimite={task.fechaLimite}
                    dificultad={task.dificultad} />
              ))}
              </>
            ): (
              <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex flex-col items-center justify-center">
                <DotLottieReact
                  src="/empty.lottie"
                  className="w-80  md:w-96 h-auto"
                  loop
                  autoplay
                />
                <div className="text-gray-800 font-bold mt-5">No hay tareas para la categoría {nombre}</div>
              </div>
            )}
          </div>
        </div>
      </section>
    </section>
  );
}
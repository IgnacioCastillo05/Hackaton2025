import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { ModalCreateCategory } from "@/common/components/ModalCreateCategorie";
import { MdOutlineEditNote } from "react-icons/md";
export function Home() {
    return (_jsx("section", { className: "w-full h-full flex flex-col items-center justify-center", children: _jsx(ModalCreateCategory, { children: _jsxs("div", { className: "cursor-pointer flex flex-col items-center justify-center p-3 rounded-md border border-gray-600 hover:bg-gray-100 transition-colors", children: [_jsx(MdOutlineEditNote, { className: "text-8xl text-gray-500 mb-4" }), _jsx("h2", { children: "Crear categor\u00EDa" })] }) }) }));
}

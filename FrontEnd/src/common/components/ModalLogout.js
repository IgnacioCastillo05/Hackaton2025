import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from "@/components/ui/dialog";
import { logoutService } from "../services/logout";
import { appLogout } from "@/store/userStore";
export function ModalLogout({ children }) {
    const handleLogout = () => {
        logoutService();
        appLogout();
    };
    return (_jsx(Dialog, { children: _jsxs("form", { children: [_jsx(DialogTrigger, { asChild: true, children: children }), _jsxs(DialogContent, { className: "sm:max-w-[425px]", children: [_jsxs(DialogHeader, { children: [_jsx(DialogTitle, { children: "Cerrar Sesi\u00F3n" }), _jsx(DialogDescription, { children: "Estas segura de que quieres cerrar sesi\u00F3n? Esto te llevar\u00E1 a la p\u00E1gina de inicio de sesi\u00F3n." })] }), _jsxs(DialogFooter, { children: [_jsx(DialogClose, { asChild: true, children: _jsx(Button, { variant: "outline", className: "cursor-pointer", children: "Cancel" }) }), _jsx(Button, { onClick: handleLogout, variant: "destructive", className: "cursor-pointer", children: "Cerrar Sesi\u00F3n" })] })] })] }) }));
}

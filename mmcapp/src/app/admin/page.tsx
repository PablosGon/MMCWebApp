"use client"

import LoadingComponent from "@/components/shared/loading.component";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function AdminPage() {

  const router = useRouter();

  useEffect(() => {
    const setAdminRights = () => {
      sessionStorage.setItem('admin', 'true');
      router.push('/');
    }

    setAdminRights();
  })

  return (
    <LoadingComponent/>
  )
}
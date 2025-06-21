"use client";

import ErrorComponent from "@/components/shared/error.component";
import LoadingComponent from "@/components/shared/loading.component";
import { CLUBS } from "@/constants/clubs-names.constant";
import { MegapigStatusColors } from "@/constants/megapig-status-colors.constant";
import { MegapigStatus } from "@/enums/megapig-status.enum";
import { ClubMember } from "@/models/club-member.model";
import { Club } from "@/models/club.model";
import { MemberMegapigReport } from "@/models/member-megapig-report.model";
import { clubService } from "@/service/club.service";
import Image from "next/image";
import Link from "next/link";
import { useParams , useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function MegapigReportPage() {

    const router = useRouter();

    const { clubid } = useParams<{ clubid: string }>();
    const clubId = parseInt(clubid);

    const [club, setClub] = useState<Club>();

    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<boolean>(false);

    const [reports, setReports] = useState<MemberMegapigReport[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data: Club = await clubService.getClub(CLUBS[clubId].id);
                setClub(data);
                setReports(data.members.map((member) => ({ playerTag: member.tag.replace('#', ''), status: MegapigStatus.Compliant })));
                setLoading(false);
            } catch {
                setError(true);
            }
        }

        fetchData();
    }, [clubId]);

    const onRadioSelect = (index: number, status: MegapigStatus) => {
        const updatedReports = [...reports];
        updatedReports[index].status = status;
        setReports(updatedReports);
    }

    const submitReport = async () => {
        setLoading(true);

        try {
            await clubService.postMegapigReport(reports);
            sessionStorage.removeItem(club!.tag)
            router.push(`/club/${clubid}`);
        } catch {
            setError(true);
        }
    }

    if (error) {
        return (
            <ErrorComponent />
        )
    } else if (loading) {
        return (
            <LoadingComponent />
        )
    } else {
        return (
            <div className="flex flex-col gap-5">
                <Link href={`/club/${clubid}`} className="text-lg">← Volver</Link>
                <header className="container bg-gray-800 p-10 rounded-4xl flex flex-wrap md:flex-nowrap items-center gap-10 justify-center">
                    <div className="flex flex-col items-center">
                        <Image src={"https://cdn.brawlify.com/club-badges/regular/" + club?.badgeId + ".png"} alt="Club Badge" width={200} height={200} className="w-30"/>
                        <h1 className="text-3xl sm:text-4xl md:text-5xl">{ club?.name }</h1>
                        <h2 className="text-xl sm:text-xl md:text-2xl">Registro de megahucha</h2>
                    </div>
                </header>
                <ul className="flex flex-wrap gap-3">
                {
                    club?.members.sort((a, b) => b.name.localeCompare(a.name)).map((member: ClubMember, index: number) => (
                        <li key={member.tag} className="bg-gray-800 p-3 rounded-2xl flex-1/4 min-w-70">
                            <div className="flex flex-row items-center gap-3">
                                <div className="text-2xl w-5">
                                    {index + 1}
                                </div>
                                <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + member.iconId + ".png"} alt={member.name} width={100} height={100} className="w-20"/>
                                <div>
                                    <h2 className="text-md lg:text-lg">{member.name}</h2>
                                    <form className="flex flex-row gap-2">
                                        {
                                            MegapigStatusColors.map((status: { status: MegapigStatus, color: string, text: string }) => (
                                                <label key={`Member ${index} ${status.status}`}>
                                                    <input 
                                                        type="radio" 
                                                        name={`report ${index}`} 
                                                        value={status.status} 
                                                        onChange={() => onRadioSelect(index, status.status)} 
                                                        className="peer hidden"
                                                        checked={reports[index].status === status.status}
                                                    />
                                                    <div
                                                        className={`
                                                        w-6 h-6 rounded-sm
                                                        ${status.color}
                                                        opacity-50
                                                        peer-checked:opacity-100
                                                        transition-colors
                                                        `}
                                                    ></div>
                                                    <span className="sr-only">{status.text}</span>
                                                </label>
                                            ))
                                        }
                                    </form>
                                    <span className={`text-sm ${MegapigStatusColors[reports[index].status.valueOf()].textColor}`}>
                                        { MegapigStatusColors[reports[index].status.valueOf()].text }
                                    </span>
                                </div>
                            </div>
                        </li>
                    ))
                }
                </ul>
                <div className="self-center">
                    <button className="bg-gray-600 rounded-xl p-3 w-20 h-12" onClick={submitReport}>Enviar</button>
                </div>
            </div>
        )
    }
}
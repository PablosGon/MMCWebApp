"use client";

import ClubMemberItem from "@/components/clubs/club-member-item.component";
import ErrorComponent from "@/components/shared/error.component";
import LoadingComponent from "@/components/shared/loading.component";
import { CLUBS } from "@/constants/clubs-names.constant";
import { ClubMember } from "@/models/club-member.model";
import { Club } from "@/models/club.model";
import { getClub } from "@/service/club.service";
import Image from "next/image";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ClubPage() {

  const { clubid } = useParams<{ clubid: string }>();
  const clubId = parseInt(clubid);

  const [club, setClub] = useState<Club | undefined>(undefined);
  const [error, setError] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getClub(CLUBS[clubId].id);
        setClub(data);  
      } catch {
        setError(true);
      }
    }
    
    fetchData();
  }, [clubId])

  if (error) {
    return (
      <ErrorComponent />
    )
  } else if (!club) {
    return (
      <LoadingComponent />
    )
  } else {
    return (
      <div className="bg">
        <Link href="/" className="text-lg">← Volver</Link>
        <header className="container bg-gray-800 p-10 rounded-4xl flex flex-wrap md:flex-nowrap items-center gap-10 justify-center lg:justify-start">
            <Image src={"https://cdn.brawlify.com/club-badges/regular/" + club?.badgeId + ".png"} alt="Club Badge" width={500} height={500} className="w-50"/>
            <div>
              <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl xl:text-9xl">{club?.name}</h1>
              <p className="text-sm sm:text-md md:text-lg lg:text-xl xl:text-2xl">{club?.description}</p>
            </div>
        </header>
        <section className="container mt-10">
          <header>
            <h2 className="text-5xl">Miembros</h2>
          </header>
          <ul className="flex flex-col gap-3">
            {
              club?.members.map((member:ClubMember, index:number) => (
                <li className="bg-gray-800 p-5 rounded-2xl " key={member.tag}>
                  <Link href={`/club/${clubId}/player/${member.tag.replace("#", "")}`}>
                    <ClubMemberItem member={member} index={index}/>
                  </Link>
                </li>
              ))
            }
          </ul>
        </section>
      </div>
    );  
  }
}

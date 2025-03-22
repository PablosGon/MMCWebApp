"use client";

import ClubMemberItem from "@/components/clubs/club-member-item.component";
import { CLUBS } from "@/constants/clubs-names.constant";
import { ClubMember } from "@/models/club-member.model";
import { Club } from "@/models/club.model";
import { getClub } from "@/service/club.service";
import Image from "next/image";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ClubPage() {

  const { id } = useParams<{ id: string }>();
  const clubId = parseInt(id);

  const [club, setClub] = useState<Club | undefined>(undefined);

  useEffect(() => {
    getClub(CLUBS[clubId].id)
      .then((club) => setClub(club))
  }, [clubId])

  if(!club){
    <p>Cargando...</p>
  } else {
    return (
      <div className="bg">
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
                    <ClubMemberItem member={member} index={index}/>
                </li>
              ))
            }
          </ul>
        </section>
      </div>
    );  
  }
}

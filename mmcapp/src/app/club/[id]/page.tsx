"use client";

import { CLUBS } from "@/constants/clubs-names.constant";
import { ClubMember } from "@/models/club-member.model";
import { Club } from "@/models/club.model";
import { getClub } from "@/service/club.service";
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

  return (
    <div className="bg pt-10">
      <header className="container bg-gray-800 p-10 rounded-4xl flex flex-wrap md:flex-nowrap items-center gap-10 justify-center">
          <img src="/badge-default.png" className="w-50"/>
          <div>
            <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl xl:text-9xl">{club?.name}</h1>
            <p className="text-sm sm:text-md md:text-lg lg:text-xl xl:text-2xl">{club?.description}</p>
          </div>
      </header>
      <section className="container mt-10">
        <header>
          <h2 className="text-5xl">Miembros</h2>
        </header>
        <ul className="flex flex-col gap-2">
          {
            club?.members.map((member:ClubMember, index:number) => (
              <li className="bg-gray-800 p-5 rounded-2xl flex flex-row items-center gap-5" key={member.tag}>
                <div className="text-2xl w-5">
                  {index + 1}
                </div>
                <img src="/profile-icon-default.png" className="w-20"/>
                <div>
                  <h2 className="text-2xl">{member.name}</h2>
                  <p>{member.role}</p>
                  <p>🏆{member.trophies}</p>
                </div>
              </li>
            ))
          }
        </ul>
      </section>
    </div>
  );
}

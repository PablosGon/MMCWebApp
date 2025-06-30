"use client";

import ClubMemberItem from "@/components/clubs/club-member-item.component";
import ErrorComponent from "@/components/shared/error.component";
import LoadingComponent from "@/components/shared/loading.component";
import { CLUBS } from "@/constants/clubs-names.constant";
import { ClubMembersOrderBy } from "@/enums/club-members-orderby.enum";
import { ClubMember } from "@/models/club-member.model";
import { Club } from "@/models/club.model";
import { clubService } from "@/service/club.service";
import Image from "next/image";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ClubPage() {

  const { clubid } = useParams<{ clubid: string }>();
  const clubId = parseInt(clubid);

  const [club, setClub] = useState<Club | undefined>(undefined);
  const [error, setError] = useState<boolean>(false);
  const [sortBy, setSortBy] = useState<ClubMembersOrderBy>(ClubMembersOrderBy.Trophies);

  const members = club?.members.sort((a, b) => {
    switch (sortBy) {
      case ClubMembersOrderBy.Trophies:
        return b.trophies - a.trophies;
      case ClubMembersOrderBy.LastWeek:
        return b.lastRegistry - a.lastRegistry;
      case ClubMembersOrderBy.Name:
        return a.name.localeCompare(b.name);
      default:
        return 0;
    }
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data: Club = await clubService.getClub(CLUBS[clubId].id);
        setClub(data);  
      } catch {
        setError(true);
      }
    }
    
    fetchData();
  }, [clubId])

  if (error) {
    return (
      <div>
        <Link href={`/`} className="text-lg">← Volver</Link>
        <ErrorComponent />
      </div>
    )
  } else if (!club) {
    return (
      <div>
        <Link href={`/`} className="text-lg">← Volver</Link>
        <LoadingComponent />
      </div>
    )
  } else {
    return (
      <div className="flex flex-col gap-5">
        <Link href="/" className="text-lg">← Volver</Link>
        <header className="container bg-gray-800 p-10 rounded-4xl flex flex-wrap md:flex-nowrap items-center gap-10 justify-center lg:justify-start">
            <Image src={"https://cdn.brawlify.com/club-badges/regular/" + club?.badgeId + ".png"} alt="Club Badge" width={500} height={500} className="w-50"/>
            <div>
              <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl">{club?.name}</h1>
              <p className="text-sm sm:text-md md:text-lg lg:text-xl">{club?.description}</p>
            </div>
        </header>
        {
          sessionStorage.getItem('admin') ? 
            <section className="self-center">
              <Link href={`/club/${clubId}/megapigReport`} className="bg-gray-600 rounded-xl p-3 w-20 h-12">
                Registrar resultados de megahucha
              </Link>
            </section>
          :
          <></>
        }
        <section className="container">
          <header className="flex items-center">
            <h2 className="text-2xl md:text5xl">Miembros</h2>
            <label className="ml-auto text-end flex flex-row">
              <p>⇅</p>
              <select onChange={(e) => setSortBy(Number(e.target.value) as ClubMembersOrderBy)} value={sortBy}>
                <option value={ClubMembersOrderBy.Trophies}>Trofeos</option>
                <option value={ClubMembersOrderBy.LastWeek}>Última semana</option>
                <option value={ClubMembersOrderBy.Name}>Nombre</option>
              </select>
            </label>

          </header>
          <ul className="flex flex-col gap-3">
            {
              members?.map((member:ClubMember, index:number) => (
                <li className="bg-gray-800 p-3 rounded-2xl " key={member.tag}>
                  <Link href={`/club/${clubId}/player/${member.tag.replace("#", "")}`}>
                    <ClubMemberItem member={member} index={index} clubId={clubId}/>
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

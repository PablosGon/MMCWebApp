import { ClubMember } from "@/models/club-member.model";
import Image from "next/image";
import { ClubMemberBadges } from "./club-member-badges.component";

export default function ClubMemberItem(params: Readonly<{ member: ClubMember, index:number }>) {

    const member = params.member;
    const index = params.index;

    return (
        <div className="flex flex-row items-center gap-3">
            <div className="text-2xl w-5">
                {index + 1}
            </div>
            <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + member.iconId + ".png"} alt={member.name} width={100} height={100} className="w-20"/>
            <div>
                <h2 className="text-md lg:text-lg">{member.name}</h2>
                <p className="text-sm lg:text-md">{member.role}</p>
                <p className="text-sm lg:text-md">🏆{member.trophies}
                    {
                        member.lastRegistry >= 0 ? 
                            <span className="text-amber-400 ml-1">(+{member.lastRegistry})</span>
                            :
                            <span className="text-teal-300 ml-1">NEW</span> 
                    } 
                </p>
                <ClubMemberBadges starBadgeCase={member.starBadgeCase} />
            </div>
            <p className="ml-auto">→</p>
        </div>
    )
}
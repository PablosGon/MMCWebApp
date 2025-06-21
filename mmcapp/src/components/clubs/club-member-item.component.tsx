import { ClubMember } from "@/models/club-member.model";
import Image from "next/image";
import { ClubMemberBadges } from "./club-member-badges.component";
import { MegapigStatus } from "@/enums/megapig-status.enum";
import { MegapigStatusColors } from "@/constants/megapig-status-colors.constant";

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
                {
                    sessionStorage.getItem('admin') ?
                        <div className="flex flex-row items-center gap-2">
                            <p>Megahucha:</p>
                            <ul className="flex flex-row gap-1">
                                {
                                    member.lastMegapigs.map((status: MegapigStatus, index) => (
                                        <li key={member.tag + index}>
                                            <div
                                                className={`
                                                w-3 h-3 rounded-sm
                                                ${MegapigStatusColors[status].color}
                                                peer-checked:opacity-100
                                                transition-colors
                                                `}
                                            ></div>
                                        </li>
                                    ))
                                }
                            </ul>
                        </div>
                    :
                        <></>
                }
            </div>
            <p className="ml-auto">→</p>
        </div>
    )
}
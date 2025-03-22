import { ClubMember } from "@/models/club-member.model";
import Image from "next/image";
import Link from "next/link";

export default function ClubMemberItem(params: { member: ClubMember, index:number }) {

    const member = params.member;
    const index = params.index;

    return (
        <Link href={"/player/" + member.tag.replace("#", "")} className="flex flex-row items-center gap-5">
            <div className="text-2xl w-5">
                {index + 1}
            </div>
            <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + member.iconId + ".png"} alt={member.name} width={100} height={100} className="w-20"/>
            <div>
                <h2 className="text-lg lg:text-2xl">{member.name}</h2>
                <p className="text-md">{member.role}</p>
                <p className="text-md">🏆{member.trophies}
                    {
                        member.lastRegistry >= 0 ? 
                            <span className="text-amber-400 ml-1">(+{member.lastRegistry})</span>
                            :
                            <span className="text-teal-300 ml-1">NEW</span> 
                    } 
                </p>
            </div>
        </Link>
    )
}
import { Club } from "@/models/club.model";
import { MemberMegapigReport } from "@/models/member-megapig-report.model";

class ClubService {

    private readonly apiUrl = process.env.NEXT_PUBLIC_API_URL;

    public async getClub(clubTag:string):Promise<Club> {

        const club = sessionStorage.getItem(clubTag);

        if (club) {
            const parsedClub: Club = await JSON.parse(club);
            return parsedClub;
        } else {
            const response = await fetch(this.apiUrl + `club/${clubTag}`);

            if (!response.ok) {
                throw new Error(`API Error: ${response.status}`);
            }

            const responseClub: Club = await response.json();
            
            sessionStorage.setItem(clubTag, JSON.stringify(responseClub));
            return responseClub;
        }

    }

    public async postMegapigReport(megapigReportMembers: MemberMegapigReport[]): Promise<boolean> {

        const response = await fetch(this.apiUrl + 'reportMegapig', {
            method: 'POST',
            body: JSON.stringify({ members: megapigReportMembers }),
            headers: { "Content-Type": "application/json" },
        })

        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }

        return true;
    }

}

export const clubService = new ClubService();
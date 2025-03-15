import { Club } from "@/models/club.model";

export async function getClub(clubTag:string):Promise<Club> {
    console.log(process.env.API_URL)
    const response = await fetch(process.env.NEXT_PUBLIC_API_URL + `club/${clubTag}`);

    if (!response.ok) {
        throw new Error(`API Error: ${response.status}`);
    }
    
    return response.json() as Promise<Club>;
}
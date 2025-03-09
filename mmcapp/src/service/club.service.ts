import { Club } from "@/models/club.model";

export async function getClub(clubTag:string):Promise<Club> {
    const response = await fetch(`http://localhost:8080/club/${clubTag}`);

    if (!response.ok) {
        throw new Error(`API Error: ${response.status}`);
    }
    
    return response.json() as Promise<Club>;
}
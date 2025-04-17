import { Season } from "@/models/season.model";

export async function getSeasons(): Promise<Season[]> {
    const response = await fetch(process.env.NEXT_PUBLIC_API_URL + `seasons`);

    if (!response.ok) {
        throw { response };
    }
    
    return response.json() as Promise<Season[]>;
}
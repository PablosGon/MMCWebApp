import Link from "next/link";

export default function Header() {
    return (
        <header className="h-25 bg-gradient-to-r from-purple-800 to-purple-950 p-5">
            <Link href="/" className="text-6xl">
                MMC App
            </Link>
            <small className="text-lg">beta</small>
        </header>
    );
}
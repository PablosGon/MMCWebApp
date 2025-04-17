import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import Header from "@/components/shared/header.component";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Mortis May Cry App",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <div className="bg-[url('../../public/background.png')] bg-cover bg-center h-screen">
          <div className="bg-gradient-to-t from-gray-950 from-10% to-transparent to-60% h-screen">
            <Header/>
            <main className="container max-w-300 mx-auto pt-20 p-5">
              {children}
            </main>
          </div>
        </div>
      </body>
    </html>
  );
}

"use client";

import Link from "next/link";
import { Home, Ticket, Info, Phone } from "lucide-react";
import { Button } from "@/components/ui/button";
import Image from "next/image";

export default function AppBar() {
  return (
    <header className="w-full shadow-md bg-gradient-to-r from-blue-600 to-blue-800 text-white">
      <div className="container mx-auto flex items-center justify-between px-4 py-3">
        <Link href="/" className="flex items-center gap-2">
          <Image
            src="/images/logo.png"
            alt="EventBooking Logo"
            width={40}
            height={40}
            className="rounded-md"
          />
          <span className="text-xl font-bold tracking-wide">EventBooking</span>
        </Link>

        <nav className="hidden md:flex gap-6 text-sm font-medium">
          <Link
            href="/events"
            className="flex items-center gap-1 hover:text-yellow-300"
          >
            <Home size={18} /> Trang chủ
          </Link>
          <Link
            href="/about"
            className="flex items-center gap-1 hover:text-yellow-300"
          >
            <Info size={18} /> Giới thiệu
          </Link>
          <Link
            href="/contact"
            className="flex items-center gap-1 hover:text-yellow-300"
          >
            <Phone size={18} /> Liên hệ
          </Link>
        </nav>

        <Link href="/login">
          <Button
            variant="secondary"
            className="rounded-full font-semibold bg-white text-indigo-600 hover:bg-indigo-50 transition"
          >
            Đăng nhập
          </Button>
        </Link>
      </div>
    </header>
  );
}

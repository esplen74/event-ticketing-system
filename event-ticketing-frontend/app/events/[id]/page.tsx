"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";

import {
  Box,
  Typography,
  Button,
  Card,
  CardContent,
  Divider,
} from "@mui/material";

interface Event {
  id: string;
  title: string;
  description: string;
  dateTime: string;
  location: string;
  imageUrl: string;
  availableTickets: number;
  gallery?: string[];
}

export default function EventDetailPage() {
  const params = useParams();
  const { id } = params;
  const [event, setEvent] = useState<Event | null>(null);

  useEffect(() => {
    const fetchEvent = async () => {
      try {
        const res = await fetch(
          `${process.env.NEXT_PUBLIC_API_URL}/events/${id}`
        );
        const data: Event = await res.json();
        setEvent(data);
      } catch (err) {
        console.error("Lỗi khi fetch event:", err);
      }
    };
    if (id) fetchEvent();
  }, [id]);

  if (!event) {
    return <Typography className="p-4">Đang tải...</Typography>;
  }
  return (
    <Box className="p-6 max-w-7xl mx-auto">
      <Box
        sx={{
          backgroundImage: `url(${
            event?.imageUrl
              ? event.imageUrl.startsWith("http")
                ? event.imageUrl
                : `/images/${event.imageUrl.replace("/images/", "")}`
              : "/images/default-banner.png"
          })`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
        className="h-[350px] rounded-h2xl relative shadow-lg"
      >
        <Box className="absolute bottom-0 left-0 right-0 bg-black/60 text-white p-4 rounded-b-2xl">
          <Typography variant="h4" fontWeight="bold">
            {event.title}
          </Typography>
          <Typography variant="subtitle1">{event.location}</Typography>
        </Box>
      </Box>

      <div className="mt-8 grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <Card className="shadow-lg rounded-2xl">
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Giới thiệu sự kiện
              </Typography>
              <Typography variant="body1" color="text.secondary">
                {event.description}
              </Typography>

              <Divider className="my-4" />

              <Typography variant="h6" gutterBottom>
                Thông tin chi tiết
              </Typography>
              <div className="space-y-1 text-gray-700">
                <p>
                  📅 Ngày:{" "}
                  {new Date(event.dateTime).toLocaleString("vi-VN", {
                    day: "2-digit",
                    month: "2-digit",
                    year: "numeric",
                  })}
                </p>
                <p>
                  ⏰ Giờ:{" "}
                  {new Date(event.dateTime).toLocaleString("vi-VN", {
                    hour: "2-digit",
                    minute: "2-digit",
                  })}
                </p>
                <p>📍 Địa điểm: {event.location}</p>
                <p>🎟️ Vé còn lại: {event.availableTickets}</p>
              </div>
            </CardContent>
          </Card>

          {/* Gallery */}
          {event.gallery && event.gallery.length > 0 ? (
            <div className="mt-6">
              <Typography variant="h6" gutterBottom>
                Hình ảnh sự kiện
              </Typography>
              <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
                {event.gallery.map((img: string, index: number) => (
                  <img
                    key={index}
                    src={
                      img && img.startsWith("http")
                        ? img
                        : "/images/no-image.png"
                    }
                    alt={`Gallery ${index}`}
                    className="rounded-xl shadow-md w-full h-40 object-cover"
                  />
                ))}
              </div>
            </div>
          ) : (
            <Typography className="mt-6" color="text.secondary">
              Hiện sự kiện chưa có hình ảnh minh họa
            </Typography>
          )}
        </div>

        <div className="lg:col-span-1">
          <Card className="shadow-lg rounded-2xl p-6 sticky top-6">
            <Typography variant="h6" gutterBottom>
              Đặt vé ngay
            </Typography>
            <Typography variant="body2" color="text.secondary" gutterBottom>
              Số vé còn lại: {event.availableTickets}
            </Typography>
            <Button
              variant="contained"
              color="primary"
              size="large"
              className="w-full mt-3"
            >
              Đặt vé
            </Button>
          </Card>
        </div>
      </div>
    </Box>
  );
}

"use client";
import { useState, useEffect } from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { motion } from "framer-motion";
import { useRouter } from "next/navigation";

const mockEvents = [
  {
    id: "1",
    title: "Concert EDM 2025",
    description: "Sự kiện âm nhạc hoành tráng nhất năm!",
    location: "Sân vận động Mỹ Đình",
    date: "2025-09-20",
    imageUrl: "/globe.svg",
    totalTickets: 200,
    availableTickets: 120,
  },
  {
    id: "2",
    title: "Hội thảo AI & Tech",
    description: "Cập nhật xu hướng công nghệ mới nhất.",
    location: "Trung tâm Hội nghị Quốc gia",
    date: "2025-10-05",
    imageUrl: "/vercel.svg",
    totalTickets: 100,
    availableTickets: 0,
  },
];

type Event = typeof mockEvents[number];

export default function EventsPage() {
  const router = useRouter();
  const [events, setEvents] = useState<Event[]>([]);

  useEffect(() => {
    setEvents(mockEvents);
  }, []);

  return (
    <Box sx={{ p: 4, background: "linear-gradient(120deg, #f8ffae 0%, #a4508b 100%)", minHeight: "100vh" }}>
      <Typography variant="h3" align="center" fontWeight={700} sx={{ mb: 4, color: "#a4508b" }}>
        Danh sách sự kiện nổi bật
      </Typography>

      <Box
        sx={{
          display: "flex",
          flexWrap: "wrap",
          gap: 4,
          justifyContent: "center",
        }}
      >
        {events.map((event) => (
          <motion.div
            key={event.id}
            whileHover={{ scale: 1.04, boxShadow: "0 8px 32px 0 #a4508b44" }}
            transition={{ type: "spring", stiffness: 300 }}
            style={{ flex: "1 1 300px", maxWidth: "350px" }}
          >
            <Card sx={{ borderRadius: 4, boxShadow: "0 4px 20px 0 #a4508b22" }}>
              <CardMedia
                component="img"
                height="180"
                image={event.imageUrl}
                alt={event.title}
                sx={{ objectFit: "cover", borderTopLeftRadius: 16, borderTopRightRadius: 16 }}
              />
              <CardContent>
                <Typography variant="h5" fontWeight={600} sx={{ color: "#a4508b" }}>
                  {event.title}
                </Typography>
                <Typography variant="body2" sx={{ mt: 1 }}>
                  {event.description}
                </Typography>
                <Typography sx={{ mt: 1, fontWeight: 500 }}>
                  <span style={{ color: "#6a82fb" }}>Thời gian:</span> {new Date(event.date).toLocaleDateString()}
                </Typography>
                <Typography sx={{ fontWeight: 500 }}>
                  <span style={{ color: "#6a82fb" }}>Địa điểm:</span> {event.location}
                </Typography>
                <Typography sx={{ mt: 1, color: event.availableTickets > 0 ? "#388e3c" : "#d32f2f" }}>
                  {event.availableTickets > 0
                    ? `Còn ${event.availableTickets}/${event.totalTickets} vé`
                    : "Hết vé"}
                </Typography>
                <Button
                  variant="contained"
                  fullWidth
                  sx={{
                    mt: 2,
                    background: "linear-gradient(90deg, #a4508b, #6a82fb)",
                    color: "white",
                    fontWeight: "bold",
                    borderRadius: 2,
                  }}
                  disabled={event.availableTickets === 0}
                  onClick={() => router.push(`/events/${event.id}`)}
                  component={motion.button}
                  whileHover={{ scale: 1.05, backgroundColor: "#6a82fb" }}
                  whileTap={{ scale: 0.98 }}
                >
                  {event.availableTickets > 0 ? "Xem & Đặt vé" : "Hết vé"}
                </Button>
              </CardContent>
            </Card>
          </motion.div>
        ))}
      </Box>
    </Box>
  );
}

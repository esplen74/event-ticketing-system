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
import CircularProgress from "@mui/material/CircularProgress";
import { AccessTime, LocationOn } from "@mui/icons-material";
import CalendarTodayIcon from "@mui/icons-material/CalendarToday";
import AccessTimeIcon from "@mui/icons-material/AccessTime";

type Event = {
  id: string;
  title: string;
  description: string;
  location: string;
  dateTime: string;
  imageUrl: string;
  totalTickets: number;
  availableTickets: number;
};

export default function EventsPage() {
  const router = useRouter();
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchEvents() {
      try {
        const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/events`);
        if (!res.ok) throw new Error("Lỗi khi fetch API");
        const data: Event[] = await res.json();
        setEvents(data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    }
    fetchEvents();
  }, []);

  if (loading) {
    return (
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          minHeight: "60vh",
        }}
      >
        <motion.div
          animate={{ rotate: 360 }}
          transition={{ repeat: Infinity, duration: 1.5, ease: "linear" }}
        >
          <CircularProgress size={60} sx={{ color: "#a4508b" }} />
        </motion.div>
        <Typography
          variant="h6"
          sx={{ mt: 2, color: "#a4508b", fontWeight: 600 }}
        >
          Đang tải sự kiện...
        </Typography>
      </Box>
    );
  }

  return (
    <Box
      sx={{
        p: 4,
         background: "#f8fafc",
        minHeight: "100vh",
      }}
    >
      <Typography
        variant="h3"
        align="center"
        fontWeight={700}
        sx={{ mb: 4, color: "#1d4ed8" }}
      >
        Danh sách sự kiện nổi bật
      </Typography>

      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fill, minmax(320px, 1fr))",
          gap: 4,
        }}
      >
        {events.map((event) => (
          <motion.div
            key={event.id}
            whileHover={{ scale: 1.04, boxShadow: "0 8px 32px 0 #a4508b44" }}
            transition={{ type: "spring", stiffness: 300 }}
          >
            <Card
              sx={{
                borderRadius: 4,
                boxShadow: "0 4px 20px 0 #a4508b22",
                height: "100%",
                display: "flex",
                flexDirection: "column",
              }}
            >
              <CardMedia
                component="img"
                height="180"
                image={event.imageUrl}
                alt={event.title}
                sx={{
                  objectFit: "cover",
                  borderTopLeftRadius: 16,
                  borderTopRightRadius: 16,
                }}
              />
              <CardContent sx={{ flexGrow: 1, lineHeight: 1.6 }}>
                <Typography
                  variant="h5"
                  fontWeight={600}
                  sx={{ color: "#111827", mb: 1 }}
                >
                  {event.title}
                </Typography>

                <Typography variant="body2" sx={{ color: "#6B7280", mb: 1 }}>
                  {event.description}
                </Typography>

                <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                  📅
                  <Typography>
                    {new Date(event.dateTime).toLocaleDateString("vi-VN")}
                  </Typography>
                </Box>

                <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                  <AccessTimeIcon fontSize="small" sx={{ color: "#ff9800" }} />{" "}
                  <Typography>
                    {new Date(event.dateTime).toLocaleTimeString("vi-VN", {
                      hour: "2-digit",
                      minute: "2-digit",
                    })}
                  </Typography>
                </Box>

                <Typography
                  sx={{
                    display: "flex",
                    alignItems: "center",
                    fontWeight: 500,
                  }}
                >
                  <LocationOn sx={{ fontSize: 20, mr: 1, color: "#6a82fb" }} />
                  {event.location}
                </Typography>

                <Typography
                  sx={{
                    mt: 1,
                    fontWeight: 500,
                    color: event.availableTickets > 0 ? "#388e3c" : "#d32f2f",
                  }}
                >
                  {event.availableTickets > 0
                    ? `Còn ${event.availableTickets}/${event.totalTickets} vé`
                    : "Hết vé"}
                </Typography>
              </CardContent>

              <motion.div
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.97 }}
              >
                <Button
                  variant="contained"
                  fullWidth
                  sx={{
                    mt: "auto",
                    py: 1.5,
                    background: "linear-gradient(90deg, #a4508b, #6a82fb)",
                    color: "white",
                    fontWeight: "bold",
                    borderRadius: 2,
                    transition: "0.3s",
                    "&:hover": {
                      background: "linear-gradient(90deg, #6a82fb, #a4508b)",
                      boxShadow: "0 6px 20px rgba(164, 80, 139, 0.4)",
                    },
                  }}
                  disabled={event.availableTickets === 0}
                  onClick={() => router.push(`/events/${event.id}`)}
                >
                  {event.availableTickets > 0 ? "Xem & Đặt vé" : "Hết vé"}
                </Button>
              </motion.div>
            </Card>
          </motion.div>
        ))}
      </Box>
    </Box>
  );
}

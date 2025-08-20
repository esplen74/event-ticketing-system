"use client";
import { useState } from "react";
import { api } from "@/lib/api";
import { Box, TextField, Button, Typography, Paper, InputAdornment } from "@mui/material";
import { motion } from "framer-motion";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import AccountCircle from "@mui/icons-material/AccountCircle";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import { useRouter } from "next/navigation";

export default function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const router = useRouter();

  const handleRegister = async () => {
    setLoading(true);
    setError("");
    setSuccess("");
    // Validate input
    if (!username || !password || !email) {
      setError("Vui lòng điền đầy đủ thông tin!");
      setLoading(false);
      return;
    }
    // Validate email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    setError("Email không hợp lệ!");
    setLoading(false);
    return;
  }
    try {
      await api.post("/users/register", { username, password, email });
      setSuccess("Đăng ký thành công! Vui lòng đăng nhập.");
      setTimeout(() => router.push("/login"), 1000);
    } catch (err: any) {
      if (err.response) {
        setError("Đăng ký thất bại! Tài khoản hoặc email đã tồn tại.");
      } else {
        setError("Không thể kết nối tới máy chủ. Vui lòng thử lại sau!");
      }
    }
    setLoading(false);
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        background: "linear-gradient(120deg, #a4508b 0%, #f8ffae 100%), radial-gradient(circle at 20% 20%, #ff6a00 0%, transparent 70%), radial-gradient(circle at 80% 80%, #6a82fb 0%, transparent 70%)",
        backgroundBlendMode: "screen, lighten",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <motion.div
        initial={{ opacity: 0, scale: 0.9, y: 40 }}
        animate={{ opacity: 1, scale: 1, y: 0 }}
        transition={{ duration: 0.7, type: "spring" }}
        style={{ width: "100%", maxWidth: 400 }}
      >
        <Paper
          elevation={12}
          sx={{
            p: 4,
            borderRadius: 5,
            backdropFilter: "blur(4px)",
            boxShadow: "0 8px 32px 0 rgba(164,80,139,0.25)",
            border: "2px solid #a4508b",
            background: "rgba(255,255,255,0.85)",
          }}
        >
          <Box sx={{ display: "flex", justifyContent: "center", mb: 2 }}>
            <motion.div
              initial={{ rotate: -20, scale: 0.8 }}
              animate={{ rotate: 0, scale: 1 }}
              transition={{ duration: 0.5 }}
            >
              <LockOutlinedIcon sx={{ fontSize: 54, color: "#a4508b" }} />
            </motion.div>
          </Box>
          <Typography
            variant="h4"
            align="center"
            gutterBottom
            fontWeight={700}
            sx={{
              background: "linear-gradient(90deg, #a4508b, #6a82fb)",
              WebkitBackgroundClip: "text",
              WebkitTextFillColor: "transparent",
            }}
          >
            Đăng ký tài khoản
          </Typography>
          <Box component="form" sx={{ mt: 2 }} onSubmit={e => e.preventDefault()}>
            <TextField
              label="Tên đăng nhập"
              variant="outlined"
              fullWidth
              margin="normal"
              value={username}
              onChange={e => setUsername(e.target.value)}
              autoFocus
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <AccountCircle sx={{ color: "#a4508b" }} />
                  </InputAdornment>
                ),
              }}
            />
            <TextField
              label="Email"
              variant="outlined"
              fullWidth
              margin="normal"
              value={email}
              onChange={e => setEmail(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <EmailOutlinedIcon sx={{ color: "#a4508b" }} />
                  </InputAdornment>
                ),
              }}
            />
            <TextField
              label="Mật khẩu"
              type="password"
              variant="outlined"
              fullWidth
              margin="normal"
              value={password}
              onChange={e => setPassword(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <LockOutlinedIcon sx={{ color: "#a4508b" }} />
                  </InputAdornment>
                ),
              }}
            />
            {error && (
              <Typography color="error" sx={{ mt: 1 }}>
                {error}
              </Typography>
            )}
            {success && (
              <Typography color="primary" sx={{ mt: 1 }}>
                {success}
              </Typography>
            )}
            <Button
              type="button"
              variant="contained"
              fullWidth
              sx={{
                mt: 3,
                py: 1.5,
                fontWeight: "bold",
                fontSize: "1rem",
                background: "linear-gradient(90deg, #a4508b, #6a82fb)",
                boxShadow: "0 4px 20px 0 #a4508b44",
                borderRadius: 3,
                letterSpacing: 1,
              }}
              onClick={handleRegister}
              disabled={loading}
              component={motion.button}
              whileHover={{ scale: 1.05, backgroundColor: "#6a82fb" }}
              whileTap={{ scale: 0.98 }}
            >
              {loading ? "Đang đăng ký..." : "Đăng ký"}
            </Button>
            <Button
              type="button"
              variant="outlined"
              fullWidth
              sx={{
                mt: 2,
                py: 1.2,
                fontWeight: "bold",
                fontSize: "1rem",
                borderRadius: 3,
                borderColor: "#a4508b",
                color: "#a4508b",
              }}
              onClick={() => router.push("/login")}
              component={motion.button}
              whileHover={{ scale: 1.05, backgroundColor: "#f8ffae" }}
              whileTap={{ scale: 0.98 }}
            >
              Quay về đăng nhập
            </Button>
          </Box>
        </Paper>
      </motion.div>
    </Box>
  );
}
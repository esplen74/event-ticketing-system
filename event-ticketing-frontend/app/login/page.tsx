"use client";
import { useState, useEffect } from "react";
import { api } from "@/lib/api";
import { Box, TextField, Button, Typography, Paper, InputAdornment } from "@mui/material";
import { motion } from "framer-motion";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import AccountCircle from "@mui/icons-material/AccountCircle";
import { useRouter } from "next/navigation";

// Khai báo interface cho phản hồi login
interface LoginResponse {
  token: string;
  user: {
    id: string;
    username: string;
    email?: string;
  };
}

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const router = useRouter();

  useEffect(() => {
  const token = localStorage.getItem("token");
  if (token) {
    api.get("/users/me", { headers: { Authorization: `Bearer ${token}` } })
      .then(() => router.push("/events"))
      .catch(() => localStorage.removeItem("token"));
  }
}, []);


  const handleLogin = async () => {
    setLoading(true);
    setError("");

    if (!username || !password) {
      setError("Vui lòng điền đầy đủ thông tin!");
      setLoading(false);
      return;
    }

    try {
      const res = await api.post<LoginResponse>("/users/login", { username, password });
      // alert("Đăng nhập thành công!");

      const { token, user } = res.data;

      // Lưu token + thông tin user
      localStorage.setItem("token", token);
      localStorage.setItem("user", JSON.stringify(user));

      router.push("/events");
    } catch (err: any) {
      if (err.response) {
        setError("Sai tài khoản hoặc mật khẩu!");
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
        background: "linear-gradient(135deg, #4f8cff 0%, #6ee7b7 100%)",
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
        <Paper elevation={8} sx={{ p: 4, borderRadius: 4, backdropFilter: "blur(2px)" }}>
          <Box sx={{ display: "flex", justifyContent: "center", mb: 2 }}>
            <motion.div
              initial={{ rotate: -20 }}
              animate={{ rotate: 0 }}
              transition={{ duration: 0.5 }}
            >
              <LockOutlinedIcon sx={{ fontSize: 48, color: "#4f8cff" }} />
            </motion.div>
          </Box>
          <Typography variant="h4" align="center" gutterBottom fontWeight={700}>
            Đăng nhập
          </Typography>
          <Box
            component="form"
            sx={{ mt: 2 }}
            onSubmit={(e) => {
              e.preventDefault();
              handleLogin();
            }}
          >
            <TextField
              label="Tên đăng nhập"
              variant="outlined"
              fullWidth
              margin="normal"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              autoFocus
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <AccountCircle color="primary" />
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
              onChange={(e) => setPassword(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <LockOutlinedIcon color="primary" />
                  </InputAdornment>
                ),
              }}
            />
            {error && (
              <Typography color="error" sx={{ mt: 1 }}>
                {error}
              </Typography>
            )}
            <Button
              type="submit"
              variant="contained"
              color="primary"
              fullWidth
              sx={{
                mt: 3,
                py: 1.5,
                fontWeight: "bold",
                fontSize: "1rem",
                boxShadow: "0 4px 20px 0 #4f8cff44",
              }}
              disabled={loading}
              component={motion.button}
              whileHover={{ scale: 1.05, backgroundColor: "#2563eb" }}
              whileTap={{ scale: 0.98 }}
            >
              {loading ? "Đang đăng nhập..." : "Đăng nhập"}
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
                borderColor: "#a4508b",
                color: "#a4508b",
                background: "white",
                borderRadius: 3,
                letterSpacing: 1,
              }}
              onClick={() => router.push("/register")}
              component={motion.button}
              whileHover={{ scale: 1.05, backgroundColor: "#f8ffae" }}
              whileTap={{ scale: 0.98 }}
            >
              Đăng ký tài khoản mới
            </Button>
          </Box>
        </Paper>
      </motion.div>
    </Box>
  );
}

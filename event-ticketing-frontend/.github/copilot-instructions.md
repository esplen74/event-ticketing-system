# Copilot Instructions for event-ticketing-frontend

## Project Overview
This is a Next.js 15 project for an event ticketing system frontend, using TypeScript, Material-UI (MUI), Tailwind CSS (via tailwind-merge), Framer Motion, and Radix UI. The backend API is expected at `http://localhost:8080/api` (see `lib/api.ts`).

## Architecture & Key Patterns
- **App Directory Structure**: Uses Next.js `/app` directory routing. Pages are in `app/`, with subfolders for routes (e.g., `app/login/page.tsx`, `app/register/page.tsx`).
- **UI Components**: Custom UI components are in `components/ui/` (e.g., `button.tsx`, `card.tsx`). Use these for consistent design.
- **Styling**: Combines MUI for rich UI components and Tailwind CSS for utility classes. Use the `cn` function from `lib/utils.ts` to merge Tailwind and classnames.
- **API Communication**: Use the `api` instance from `lib/api.ts` (Axios, baseURL set) for all HTTP requests. Do not hardcode URLs in components.
- **Client-Side Navigation**: Use `useRouter` from `next/navigation` and `router.push()` for navigation. Avoid `window.location.href` to prevent full page reloads.
- **Form Handling**: Forms use React state and MUI `TextField`. For validation, use inline checks or integrate with `react-hook-form` and `zod` if needed.
- **Animations**: Use Framer Motion for UI transitions and button effects.
- **Font Management**: Fonts are loaded via `next/font` in `app/layout.tsx`.

## Developer Workflows
- **Start Dev Server**: `npm run dev` (uses Turbopack)
- **Build**: `npm run build`
- **Lint**: `npm run lint`
- **Deploy**: Designed for Vercel, but can run locally

## Conventions & Patterns
- **Session/Auth**: No backend/session logic yet; plan to use session for login state in future.
- **Error Handling**: Show errors using MUI `Typography` with `color="error"`.
- **Success Handling**: Show success messages and redirect after short delay (see registration flow).
- **Navigation**: Always use `router.push` for route changes. Example: `onClick={() => router.push("/login")}`.
- **Component Usage**: Prefer custom UI components in `components/ui/` for buttons, forms, dialogs, etc.
- **API Usage**: Always use the shared `api` instance for requests.

## Integration Points
- **External Libraries**: MUI, Framer Motion, Radix UI, Axios, Tailwind CSS, Zod, React Hook Form.
- **Backend API**: All requests go to `/api` on `localhost:8080` via Axios instance.

## Example Patterns
- **Login Form**: See `app/login/page.tsx` for stateful form, error handling, and navigation.
- **Register Form**: See `app/register/page.tsx` for validation, success/error handling, and navigation.
- **Custom Button**: Use `components/ui/button.tsx` for consistent button styles.
- **Classname Merge**: Use `cn()` from `lib/utils.ts` to combine Tailwind and other classes.

## Key Files & Directories
- `app/` - Next.js pages/routes
- `components/ui/` - Custom UI components
- `lib/api.ts` - Axios API instance
- `lib/utils.ts` - Utility functions (e.g., `cn`)
- `app/layout.tsx` - Global layout and font setup

---

If any conventions or workflows are unclear, please ask for clarification or examples from the codebase.

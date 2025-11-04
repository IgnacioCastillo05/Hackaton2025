@echo off
REM Script to run Pixel-Scribe with environment variables

setlocal enabledelayedexpansion

set "SUPABASE_BUCKET=pixel-scribe"
set "SUPABASE_REGION=us-east-2"
set "SUPABASE_ENDPOINT=https://dflbwhbdpghrmeenzjgg.storage.supabase.co/storage/v1/s3"
set "SUPABASE_ACCESS_KEY_ID=ef7bdcd3123223d26728171e657725e4"
set "SUPABASE_SECRET_ACCESS_KEY=a33edb6e788dd288d71d4f60f1e4fd703c265f460ff4750168e12624aa17f5ec"
set "N8N_WEBHOOK_URL=https://magia.app.n8n.cloud/webhook/process-image"

mvnw.cmd spring-boot:run -DskipTests

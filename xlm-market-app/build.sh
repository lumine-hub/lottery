
# 普通镜像构建，随系统版本构建 amd/arm
#docker build -t xuluming/xlm-market-app:1.0 -f ./Dockerfile .
docker buildx build --platform linux/amd64,linux/arm64 -t xuluming/xlm-market-app:1.0 --push .
# 兼容 amd、arm 构建镜像
# docker bdocker buildx build --platform linux/amd64,linux/arm64 -t xuluming/xlm-market-app:1.0 --push .uildx build --load --platform liunx/amd64,linux/arm64 -t xuluming/xlm-market-app:1.0 -f ./Dockerfile . --push
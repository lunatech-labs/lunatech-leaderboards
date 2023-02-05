#!/bin/bash
cd "$(dirname "$0")"
set -e
NEW_VERSION=$1
cat > ../../manifests/lunagraph/.argocd-source-lunagraph.yml << EOF
kustomize:
  images:
  - ghcr.io/lunatech-labs/lunacloud-leaderboards:$NEW_VERSION
EOF
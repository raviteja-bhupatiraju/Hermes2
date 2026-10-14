#!/bin/sh
# Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
#
# Development helper: compiles the sources of edu.louisiana.caai.medbiq.model (the MedBiquitous standards) into
# a scratch folder and runs the hermes command line tool with them, reading the models, schemas and templates of
# the working copy. Lets you check templates, samples, titles and rules without a Maven build:
#
#   tools/dev-hermes.sh validate bundles/edu.louisiana.caai.medbiq.model/samples/curriculum-inventory
#   tools/dev-hermes.sh info some-document.xml
#   tools/dev-hermes.sh --compile-only
#
# Needs a build of the CLI distribution (releng/edu.louisiana.caai.lom.cli.dist/target/cli, made by
# "mvnw package") for the other jars, and JAVA_HOME pointing to JDK 25 or later.
# Set DEV_HERMES_OUT to use a separate output folder (e.g. when several people or agents compile at once).
set -e
repo=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
if command -v cygpath >/dev/null 2>&1; then
  repo=$(cygpath -m "$repo")  # Windows Java needs C:/... paths
fi
bundle="$repo/bundles/edu.louisiana.caai.medbiq.model"
lib="$repo/releng/edu.louisiana.caai.lom.cli.dist/target/cli/lib"
out="${DEV_HERMES_OUT:-$repo/bundles/edu.louisiana.caai.medbiq.model/target/dev-classes}"
if command -v cygpath >/dev/null 2>&1; then
  out=$(cygpath -m "$out")
fi
osgi=$(ls "$HOME"/.m2/repository/p2/osgi/bundle/org.eclipse.osgi/*/*.jar 2>/dev/null | head -1)
if command -v cygpath >/dev/null 2>&1 && [ -n "$osgi" ]; then
  osgi=$(cygpath -m "$osgi")
fi
java="${JAVA_HOME:+$JAVA_HOME/bin/}java"
javac="${JAVA_HOME:+$JAVA_HOME/bin/}javac"
case "$(uname -s)" in
  CYGWIN*|MINGW*|MSYS*) sep=';' ;;
  *) sep=':' ;;
esac
if [ ! -d "$lib" ]; then
  echo "dev-hermes: build the CLI distribution first (mvnw package)" >&2
  exit 2
fi
others=""
for jar in "$lib"/*.jar; do
  case "$jar" in
    *edu.louisiana.caai.medbiq.model*) ;;
    *) others="$others$sep$jar" ;;
  esac
done
rm -rf "$out"
mkdir -p "$out"
find "$bundle/src" -name '*.java' > "$out/sources.txt"
"$javac" -nowarn -encoding UTF-8 --release 25 -d "$out" -cp "$osgi$others" @"$out/sources.txt"
if [ "$1" = "--compile-only" ]; then
  echo "Compiled to $out"
  exit 0
fi
exec "$java" -cp "$out$sep$bundle$others" edu.louisiana.caai.lom.cli.Hermes "$@"

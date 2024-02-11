{ pkgs ? import <nixpkgs> {}, jdk ? "jdk17" }:

  pkgs.mkShell {
    buildInputs = [
      pkgs.${jdk}
      pkgs.gradle
      pkgs.jq
    ];
    shellHook = ''
      export NIX_ENV=dev
    '';
  }
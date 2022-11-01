module.exports = {
    coverageThreshold: {
        global: {
            branches: 0,
            functions: 0,
            lines: 0,
            statements: 0
        }
    },
    modulePathIgnorePatterns: [
        "<rootDir>/dist/"
    ],
    coverageDirectory: "build_internal/test_results",
    reporters: ["jest-standard-reporter", "jest-junit"],
    collectCoverage: true,
    collectCoverageFrom:  [
        "src/**/*.{ts,tsx,js,jsx}",
        "lib/**/*.{ts,tsx,js,jsx}"
    ],
    transform: {
        "^.+\\.(ts|tsx|js|jsx)$": "ts-jest",
    },
    setupFiles: ["<rootDir>/test/setup.ts"],
}

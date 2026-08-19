# AI-Powered Personalized Spring Boot Tutor — Product Requirements

**Initial platform:** Java and Spring Boot learning platform  
**Frontend:** React + TypeScript  
**Backend:** Spring Boot + Java  
**Version:** 1.0 — 19 August 2026

## Product summary

The product is a structured technical mentor, not a general-purpose chatbot. It takes a learner from their current level toward practical SDE-1 backend readiness through a personalized Spring Boot roadmap, concise instruction, immediate implementation, project work, and durable learner memory.

The learner chooses Spring Boot, describes existing knowledge, may optionally complete an assessment, chooses a target level, and receives a personalized roadmap. Each phase combines theory, examples, optional quizzes, implementation, and project integration.

## Product principles

- Personalize before teaching.
- Teach concise theory, then connect it to examples and implementation.
- Keep assessments and quizzes optional; they must never block progress.
- Store durable learner state, preferences, strengths, weaknesses, misconceptions, and project context.
- Retrieve relevant context rather than replaying the full course history to an LLM.
- Prefer hints, constraints, reviews, and debugging over simply supplying complete solutions.
- Build the platform with production-oriented engineering practices.

## Initial scope

- Learner account and profile.
- Spring Boot curriculum and personalized roadmap.
- Optional assessment engine and quizzes.
- Learning sessions, progress tracking, and persistent learner memory.
- Project-based learning, code submissions, and AI code review.
- Adaptive recommendations and a RAG knowledge base.
- Authentication, authorization, analytics, and a later Docker/deployment learning track.

The initial release does not include multi-framework support, human mentoring, professional certification, or microservices from day one.

## Learning and assessment requirements

The curriculum is a dependency-aware skill graph. Topics define prerequisites, objectives, implementation relevance, optional assessment content, and project mappings.

Assessment and quiz requirements:

- All assessments and quizzes are optional.
- Skipping one must never lock the learner out of the next topic.
- Results are learning signals, not absolute measures of ability.
- Missing quiz data is not evidence of failure.
- Learners can retake quizzes and request explanations for incorrect answers.

## Persistent learner memory

Persistent memory is a core product feature. The system stores structured learner state and durable learning memories locally; semantic embeddings may support retrieval later. For an interaction, the context builder retrieves only the current roadmap state, relevant durable memories, recent session context, and useful technical documentation.

Memory must not persist every chat turn. It should favor durable facts, repeated patterns, preferences, decisions, mistakes, and project state. Memories should have timestamps and provenance where useful, use confidence or evidence counts for inference, and be viewable, correctable, and deletable by the learner.

## AI tutor and knowledge grounding

The tutor explains concepts at the learner's level, asks guiding questions, provides hints before full solutions, generates optional quizzes and implementation tasks, reviews code, explains errors, connects current topics to prior learning, and summarizes durable memory candidates.

The knowledge layer should prioritize authoritative sources, initially official Spring and Java documentation. The backend exposes a provider-agnostic AI service abstraction so model providers can change without changing learning-domain logic. Structured outputs are preferred for roadmaps, quizzes, memory extraction, and code-review metadata.

## Project-based learning

Learning topics map to concrete project tasks. The progression is:

1. Personal Task Management API — REST CRUD, DTOs, validation, exception handling, pagination, JPA, database integration, OpenAPI, and unit tests.
2. Collaborative Event & Resource Management Platform — authentication, RBAC, notifications, uploads, search, caching, background jobs, Docker, CI/CD, and deployment.
3. AI-Powered Developer Collaboration Platform — teams and projects, AI assistance, document ingestion, RAG, recommendations, messaging, observability, cloud deployment, and advanced architecture.

## Architecture and security direction

Start with a modular Spring Boot monolith, with internally separated modules for auth, user, learning, roadmap, assessment, quiz, project, progress, memory, AI, RAG, code review, and notifications. PostgreSQL is the primary data store; Redis, vector memory, RAG, Docker, CI/CD, AWS deployment, and asynchronous processing are introduced in later release stages.

Use authentication and authorization, secure password handling, ownership checks for learner resources, validation and request limits, AI-endpoint rate limits, external secret management, audit logging for sensitive actions, memory privacy controls, and prompt-injection protections.

## Definition of done for future features

- Verify the intended user flow.
- Add applicable validation, authorization, persistence changes, and tests.
- Clearly distinguish optional and mandatory learner actions.
- Ensure skipped quizzes never block learning.
- Use validated AI contracts where applicable and handle provider failures gracefully.
- Add meaningful diagnostics for production operations.

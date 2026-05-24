package com.example.ui

// Custom data classes for Quantum Leap App
enum class QuantumLevel(
    val value: Float,
    val title: String,
    val description: String,
    val bubbleText: String
) {
    KIDS(0.0f, "Quantum Magic", "For young explorers!", "🐶 Magic Pets & Spells!"),
    MIDDLE_SCHOOL(0.7f, "Young Minds", "For curious middle schoolers!", "🎈 Energy Strings & Codes!"),
    HIGH_SCHOOL(1.3f, "Quantum Lab", "For high school & young college students!", "🔬 State Collapses & Gates!"),
    POST_DOC(2.0f, "Hilbert Lab", "For quantum mechanics professionals!", "🧬 Density Matrices & Operators!")
}

data class ConceptLesson(
    val title: String,
    val tagline: String,
    val mainText: String,
    val analogyTitle: String,
    val analogyText: String,
    val facts: List<String>,
    val advancedMath: String = ""
)

data class QuizQuestion(
    val levelKey: String, // "KIDS", "MIDDLE_SCHOOL", "HIGH_SCHOOL", "POST_DOC"
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

object QuantumRepository {
    
    fun getLevelFromSlider(value: Float): QuantumLevel {
        return when {
            value < 0.5f -> QuantumLevel.KIDS
            value < 1.1f -> QuantumLevel.MIDDLE_SCHOOL
            value < 1.7f -> QuantumLevel.HIGH_SCHOOL
            else -> QuantumLevel.POST_DOC
        }
    }

    // Entanglement lessons
    val entanglementLessons = mapOf(
        QuantumLevel.KIDS to ConceptLesson(
            title = "Magical Pet Twin Sparkles",
            tagline = "Two magical puppies that act like one!",
            mainText = "Imagine you have two special, magical puppies. They love playing hide-and-seek. But they have a superpower: they are 'Quantum Puppies'! If you put them in two closed boxes and send one box to the Moon, they are both spinning in a rainbow of sparkles. Nobody knows what they look like yet!",
            analogyTitle = "The Magic Blue/Red Ribbon",
            analogyText = "The very second you open Box A and see a Blue puppy, Box B instantly collapses from its rainbow into a Blue puppy too! They didn't send a text message—they just 'knew' magically. Albert Einstein called this 'Spooky Action at a Distance'!",
            facts = listOf(
                "🐶 Opening one box immediately tells you what is in the other box!",
                "🌌 They can be across the universe, but they are still best friends!",
                "🌈 Before you open the box, the puppy is every color at once (Superposition)!"
            )
        ),
        QuantumLevel.MIDDLE_SCHOOL to ConceptLesson(
            title = "Invisible Quantum String",
            tagline = "A connection stronger than any steel string!",
            mainText = "In the quantum world, things don't behave like normal balls. Two particles can be made together in a way that their characteristics (like spin) are locked together. We call them 'Entangled'. It is as if they are connected by a super-magical invisible string that never breaks, no matter how far they go.",
            analogyTitle = "The Interstellar Spinning Coins",
            analogyText = "If you spin two entangled coins, they both keep spinning in a blur of heads and tails. Once you stop Coin A and see 'Heads', Coin B instantly stops spinning and landed on 'Tails'! They always show opposite sides, instantly!",
            facts = listOf(
                "🧵 Entanglement links particles together so they act like a single system.",
                "📡 Tapping/measuring one particle forces the other to choose its state.",
                "⚠️ You cannot use this to send messages faster than light, because the coin flip is random!"
            )
        ),
        QuantumLevel.HIGH_SCHOOL to ConceptLesson(
            title = "Spin Collapses & Superposition",
            tagline = "Probability and Correlation in Spin States",
            mainText = "Quantum particles possess intrinsic angular momentum called 'Spin'. An entangled pair in a singlet state is represented as |ψ⟩ = (1/√2)(|↑↓⟩ - |↓↑⟩). This means if one particle is measured to be Spin-Up (↑), the other is guaranteed to be Spin-Down (↓). Until a measurement occurs, however, neither particle has a definite spin; they exist in a linear superposition of both states.",
            analogyTitle = "The Bell Multi-axis Test",
            analogyText = "When Alice measures Spin along the Z-axis, she gets '+1' (Up) with 50% probability. Instantly, Bob's particle collapses into the '-1' (Down) state with 100% correlation. This correlation holds even if the measurements are done at spacelike separations, defying physical 'local realism'!",
            facts = listOf(
                "🌀 Superposition allows spin along any axis to remain unfixed until measured.",
                "💥 Measurement collapses the wavefunction, forcing a coordinate state.",
                "📐 Bell's Theorem proves that quantum mechanics violates local hidden variables!"
            )
        ),
        QuantumLevel.POST_DOC to ConceptLesson(
            title = "Hilbert Space & Mixed State Density Matrices",
            tagline = "Concurrence, Von Neumann Entropy & Decoherence Channels",
            mainText = "For bipartite systems in a tensor product Hilbert space H_A ⊗ H_B, a pure entangled state cannot be factored into product states: |ψ_AB⟩ ≠ |φ_A⟩ ⊗ |ξ_B⟩. Crucially, for a state in the singlet configuration |ψ⁻⟩ = (1/2)(|01⟩ - |10⟩), the total density matrix is ρ = |ψ⁻⟩⟨ψ⁻|. Evaluating the partial trace yields a maximally mixed reduced state for A: ρ_A = Tr_B(ρ) = (1/2)I_2, which yields a Von Neumann Entropy of S(ρ_A) = -Tr(ρ_A log₂ ρ_A) = 1 bit of entropic contribution.",
            analogyTitle = "Decoherence via Partial Dephasing",
            analogyText = "As the quantum system interacts with a thermal reservoir, environmental coupling introduces a decoherence factor λ ∈ [0, 1]. The state degrades into ρ(λ) = (1-λ)|ψ⁻⟩⟨ψ⁻| + λ I/4. We compute the Concurrence C(ρ) = max(0, 1 - 1.5λ) to measure remaining entanglement. Watch the system transition from non-local quantum correlations to classical mixtures as concurrence collapses to 0!",
            facts = listOf(
                "🧬 Pure state entanglement is measured quantitatively by entanglement of formation and concurrence.",
                "⚗️ Thermal noise collapses off-diagonal coherence terms, yielding purely mixed state mixtures.",
                "🔮 Local measurements of system properties are modeled via Positive Operator-Valued Measures (POVMs)."
            )
        )
    )

    // Teleportation lessons
    val teleportationLessons = mapOf(
        QuantumLevel.KIDS to ConceptLesson(
            title = "The Magic Hat Transporter",
            tagline = "Sending a magic pet's costume across the galaxy!",
            mainText = "Quantum Teleportation isn't like Star Trek where we physically throw a puppy. Instead, we have Puppy A wearing a beautiful sparkling golden crown! We also have a twin pair of entangled puppies (Puppy B and Puppy C) sitting separated: Puppy B is with Alice, and Puppy C is with Bob on Mars.",
            analogyTitle = "Copying the Magic Crown State",
            analogyText = "Alice does a special magic spell on her two puppies (A and B). This destroys her original puppy A's crown, but she gets a 2-digit magical key (like '1 2'!). She speaks this key over a space radio to Bob. Bob hears '1 2', rotates his Magic Puppy C, and *bam*! Puppy C is now wearing the exact glowing golden crown!",
            facts = listOf(
                "👑 The actual puppy is NOT physically flying. Only his 'crown state' is copied!",
                "📻 Bob cannot get the crown until Alice calls him with the classical radio key!",
                "👻 The original crown on A is destroyed during the spell—this is 'The No-Cloning Theorem'!"
            )
        ),
        QuantumLevel.MIDDLE_SCHOOL to ConceptLesson(
            title = "The Quantum Secret Fax Machine",
            tagline = "Beaming secure information using keys",
            mainText = "Quantum teleportation is a secure way to transfer a physical particle's exact quantum 'vibe' to another particle without moving the particle! To do this, Alice and Bob need a shared secret key (an entangled pair of particles). Alice wants to teleport her particle (let's call it the Message). She measures the Message and her half of the key together. This gives her two regular numbers.",
            analogyTitle = "Tuning Bob's Dial",
            analogyText = "Alice texts those two numbers to Bob. Bob looks at his particle and says, 'Aha! Alice's numbers tell me exactly how to twist my particle's dial!' He twists it, and instantly his particle becomes an exact twin of Alice's original Message! Meanwhile, Alice's original particle is wiped blank.",
            facts = listOf(
                "📠 Quantum teleportation acts like a cosmic fax machine that deletes the original after sending.",
                "🔑 The 'FAX' is 100% secure from hackers! Eavesdroppers can't read the message without the key.",
                "🚀 The speed limit of communication. It cannot work faster than Alice can text Bob the numbers."
            )
        ),
        QuantumLevel.HIGH_SCHOOL to ConceptLesson(
            title = "The Bell-State Teleporter Protocol",
            tagline = "Applying CNOT and Hadamard Gates to teleport state |ψ⟩",
            mainText = "To teleport an unknown qubit state |ψ⟩ = α|0⟩ + β|1⟩ from Alice to Bob:\n1. Alice and Bob share an auxiliary EPR pair: |Φ⁺⟩ = (1/√2)(|00⟩ + |11⟩). At this step, the composite state of the three qubits is |Ψ_0⟩ = (α|0⟩ + β|1⟩) ⊗ (1/√2)(|0_B 0_C⟩ + |1_B 1_C⟩).\n2. Alice passes her qubits through a CNOT gate followed by a Hadamard (H) gate. This entangles the message qubit with Alice's local EPR qubit B.",
            analogyTitle = "Classical Correction Gates",
            analogyText = "Alice measures both her qubits, obtaining two classical bits: b₁ b₂ ∈ {00, 01, 10, 11}. This measurement projects Bob's qubit C into a unique state related to Alice's input:\n- If 00: Bob applies I (No change)\n- If 01: Bob applies X (bit-flip)\n- If 10: Bob applies Z (phase-flip)\n- If 11: Bob applies ZX (both flips).\nBob's local state C is now exactly identical to |ψ⟩!",
            facts = listOf(
                "🔌 Alice's measurements collapse her qubits, destroying the input |ψ⟩ conforming to the No-Cloning theorem.",
                "📞 Classical communication (2 bits) is required, limiting the transfer speed to the speed of light.",
                "🎛️ Unitary gates (X and Z) act as the physical dials Bob applies to recover the state."
            )
        ),
        QuantumLevel.POST_DOC to ConceptLesson(
            title = "Rigorous Algebraic State Teleportation",
            tagline = "Multi-Qubit Unitary Transformations & State Collapse Analysis",
            mainText = "Let Alice hold Qubit 1 in state |ψ⟩ = α|0⟩ + β|1⟩ and Qubit 2, where Qubits 2 & 3 are in the Bell state |Φ⁺⟩ = (1/√2)(|00⟩ + |11⟩). The initial state is |Ψ_0⟩ = (1/√2)[α|000⟩ + α|011⟩ + β|100⟩ + β|111⟩]. Alice applies the local unitary U_Bell = (H ⊗ I)(CNOT_{1→2} ⊗ I) which maps to:\n|Ψ_1⟩ = (1/2) [ |00⟩(α|0⟩ + β|1⟩) + |01⟩(α|1⟩ + β|0⟩) + |10⟩(α|0⟩ - β|1⟩) + |11⟩(α|1⟩ - β|0⟩) ].",
            analogyTitle = "Projections to Bob's Local Operators",
            analogyText = "We rewrite the state in the joint Bell-basis of Alice's qubits: |Ψ_1⟩ = (1/2) [ |Φ⁺⟩ I |ψ⟩ + |Ψ⁺⟩ X |ψ⟩ + |Φ⁻⟩ Z |ψ⟩ + |Ψ⁻⟩ ZX |ψ⟩ ]. Thus, performing a local projective Bell State Measurement (BSM) on qubits 1 and 2 collapses Bob's subsystem into U_corr |ψ⟩, where U_corr ∈ {I, X, Z, ZX}. Classical transmission of the outcomes selects the corrective local projection U_corr_dagger ∈ {I, X, Z, XZ} which Bob executes, ensuring the state vector satisfies ρ_Bob = |ψ⟩⟨ψ| identically.",
            facts = listOf(
                "💫 Teleportation acts as a quantum channel with perfect fidelity under ideal conditions.",
                "🛸 Physical matter does not travel. Quantum information is transferred via entanglement and classical signaling.",
                "📊 The state |ψ⟩ can represent a coordinate in an infinite-dimensional density space if continuous variable (CV) quantum states are used."
            )
        )
    )

    // Quiz questions Database
    val quizDatabase = listOf(
        // Kids level questions
        QuizQuestion(
            levelKey = "KIDS",
            question = "If our magic quantum puppies always wear coordinates outfits, what happens when we open Box A to find a sparkling BLUE puppy?",
            options = listOf(
                "The other box instantly has a BLUE puppy too!",
                "The other box has a cat!",
                "The other box stays in a colorful rainbow forever."
            ),
            correctIndex = 0,
            explanation = "Since the puppies are entangled (magically linked!), finding a blue puppy in Box A instantly collapses Box B into a matching blue puppy, no matter how far apart they are!"
        ),
        QuizQuestion(
            levelKey = "KIDS",
            question = "When Alice teleports her magic puppy's golden crown to Bob, what happens to Alice's original crown?",
            options = listOf(
                "Alice gets a second copy of the crown.",
                "Alice's crown disappears (it's destroyed to build Bob's copy!).",
                "It changes into a banana!"
            ),
            correctIndex = 1,
            explanation = "That's the No-Cloning Theorem! In quantum teleportation, you cannot create copies. The original state is destroyed to build the new one."
        ),
        
        // Middle School questions
        QuizQuestion(
            levelKey = "MIDDLE_SCHOOL",
            question = "Does quantum entanglement allow us to send secret messages faster than the speed of light?",
            options = listOf(
                "Yes, because the other particle changes instantly!",
                "No, because the measurement outcome is random and we still need a classical message (like a phone call) to decode it.",
                "Only on Tuesdays."
            ),
            correctIndex = 1,
            explanation = "Measurement outcome is random. Bob cannot know what Alice measured or how to decode his particle without Alice sending her result classical-communication style (which is limited by the speed of light)."
        ),
        QuizQuestion(
            levelKey = "MIDDLE_SCHOOL",
            question = "In quantum teleportation, what is physically sent through space to transfer the state?",
            options = listOf(
                "The original particle itself takes a ride in a spaceship.",
                "Nothing but classical information (like two numbers) to tune the receiver's target particle.",
                "Gravity waves only."
            ),
            correctIndex = 1,
            explanation = "Only classical bits (just regular numbers) are sent through physical space! The particle itself does not move; Bob's local particle simply transforms into the original state."
        ),

        // High School level questions
        QuizQuestion(
            levelKey = "HIGH_SCHOOL",
            question = "Which of the following gates is used in quantum circuits to entangle two initially separate qubits?",
            options = listOf(
                "NOT gate",
                "Controlled-NOT (CNOT) gate combined with a Hadamard (H) gate",
                "OR gate"
            ),
            correctIndex = 1,
            explanation = "To create a Bell state (such as (|00⟩+|11⟩)/√2), you apply a Hadamard gate to qubit 1 followed by a CNOT gate with qubit 1 as the control and qubit 2 as the target."
        ),
        QuizQuestion(
            levelKey = "HIGH_SCHOOL",
            question = "If Alice measurements register the classical outcome '11' in the teleportation protocol, what quantum correction gates should Bob apply?",
            options = listOf(
                "Identity gate (I)",
                "X gate followed by Z gate (ZX correction)",
                "Z gate only"
            ),
            correctIndex = 1,
            explanation = "If Alice measures '11', Bob's qubit is projected into the state ZX|ψ⟩. To recover |ψ⟩, Bob must apply the inverse operators which corresponds to Z and X gates!"
        ),

        // Post Doc questions
        QuizQuestion(
            levelKey = "POST_DOC",
            question = "If of a bipartite state ρ, if we evaluate the partial trace ρ_A = Tr_B(ρ) and find the Von Neumann Entropy S(ρ_A) = 1, what does this imply about the global system?",
            options = listOf(
                "The global system is in a product state.",
                "The subsystem A is maximally entangled with subsystem B, and the global system is in a pure entangled state.",
                "The global system has disintegrated due to thermal decoherence."
            ),
            correctIndex = 1,
            explanation = "For a pure bipartite system, if the reduced density matrix ρ_A is maximally mixed, it represents maximal entanglement, giving S(ρ_A) = log_2 2 = 1 bit."
        ),
        QuizQuestion(
            levelKey = "POST_DOC",
            question = "An entangled singlet state ρ passes through a partial phase-damping channel causing decoherence λ = 0.4. What is the Concurrence C(ρ(λ)) of the degraded state?",
            options = listOf(
                "1.00",
                "0.40",
                "0.00"
            ),
            correctIndex = 1,
            explanation = "Evaluating concurrence for the dephased singlet state under decoherence gives C = max(0, 1 - 1.5λ). For λ=0.4, we have C = max(0, 1 - 0.6) = 0.4."
        )
    )
}

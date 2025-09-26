# Gradient descent

I'll look into some varieties of Gradient descent in this topic. I'll prove their convergence and make some comparisons on diffrent functions.

First of all we need to know what gradient of function is. 

## Gradient 
---
> The gradient of scalar function f: $\mathbb{R^n} \to \mathbb{R}$ at a point x is the vector of its partial derivatives:

$$
\nabla f(x) = \left( \frac{\partial f}{\partial x_1}(x), \dots, \frac{\partial f}{\partial x_n}(x) \right)^{\top}.
$$

**Meaning & properties**

- It points in the direction of steepest increase of $f$.
- Its length $\|\nabla f(x)\|$ equals the maximum rate of increase (the maximum directional derivative) at $x$.
- It is orthogonal to level sets/level surfaces of $f$.

**Directional derivative relation.** For a unit vector $u$:

$$
D_{u} f(x)=\nabla f(x)\cdot u.
$$

Where $D_u$ — directional derivative and $u$ — this direction. And 

$$
D_{\mathbf u} f(\mathbf x)
= \sum_{i=1}^{n} \frac{\partial f}{\partial x_i}(\mathbf x)\,\cos\alpha_i.
$$

Here, $\alpha_i$ is the angle between the unit direction vector $\mathbf{u}$ and the $i$-th coordinate axis (basis vector $\mathbf{e}_i$). In an orthonormal basis, the direction cosine is
$\cos\alpha_i = \mathbf{u} \cdot \mathbf{e}_i = u_i$.

**Example.**
If $f(x,y)=x^2+3y$, then $\nabla f(x,y)=(2x,\,3)$.

## Intro
The main problem is find minimum of scalar function $f:\mathbb{R^n} \to \mathbb{R}$:

$$
\min_{x \in \mathbb{R}^n} f(x)
$$

Let us always be able to calculate the gradient of $f(x)$. Let's look at 3 varieties:
- Constant-step gradient descent method
- Gradient descent method with fractional steps
- Steepest descent method.

### Algo
As is known (easy to check yourself) the fastest descent has the value opposite gradient value $(-\nabla f(x))$.
The core idea is to move the iterate in the **direction of greatest decrease**—the negative gradient. At iteration $k$ we update

$$
x^{[k+1]} \=\ x^{[k]} - \lambda^{[k]}\,\nabla f\big(x^{[k]}\big),
$$

where the stepsize $\lambda^{[k]}$ can be chosen in different ways:

- **Constant stepsize:** $\lambda^{[k]}=\lambda>0$ (simple but may diverge if $\lambda$ is too large).
- **Diminishing steps:** reduce $\lambda^{[k]}$ over time (e.g., multiply by a factor or use a schedule) to ensure convergence.
- **Steepest-descent line search:** pick the best step along the ray

$$
\lambda^{[k]} \=\ \arg\min_{\lambda\ge 0}\ f\!\big(x^{[k]}-\lambda\,\nabla f(x^{[k]})\big).
$$

### Stop
And of course we have to stop sooner or later. For this we need to have some rules. Stopping rules for approximate minimization can be based on several signals. Common ones are:
- **Small iterate change:**  
  $\|x^{[k+1]} - x^{[k]}\| \le \varepsilon$
- **Small objective change:**  
  $\big|\,f(x^{[k+1]}) - f(x^{[k]})\,\big| \le \varepsilon$

Here, $x^{[k]} \in \mathbb{R}^n$ is the point after the $k$-th iteration, and $\varepsilon>0$ is a user-chosen tolerance.

## Constant-step gradient descent method

So **we will consider functions that satisfy additional regularity conditions** (e.g., Lipschitz gradient) because these assumptions supply the needed curvature control to derive the descent inequality, guarantee sufficient decrease with a simple constant step size, and prove convergence to stationary points. In the fully general differentiable case, no such universal guarantees can be established.
- The descent lemma can fail, so $f(x^{k+1})$ may increase even with small fixed $\lambda$ monotonicity is lost.
- We have no control on how fast $\nabla f$ changes, so steps can overshoot, diverge, or enter cycles $\|\nabla f(x^{k})\|$ need not go to $0$.
- Classical $C^1$ counterexamples exist where $\nabla f$ is not Lipschitz (e.g., $f(x)=|x|^{4/3}$ in 1D has $\nabla f(x)=\tfrac{4}{3}\,\mathrm{sign}(x)\,|x|^{1/3}$), and fixed-step GD can oscillate or fail to decrease for many $\lambda$.
- Being merely “bounded below” does not prevent wandering on large flat regions or around saddles in nonconvex landscapes.

Setup. $\lambda^{[k]} = \lambda = \text{const},\quad f:\mathbb{R}^n\to\mathbb{R}$ is differentiable and bounded below.

Assume a Lipschitz gradient:

$$
\|\nabla f(x)-\nabla f(y)\|\le L\|x-y\|\quad\text{for all }x,y,\qquad 0<\lambda<\tfrac{2}{L}.
$$

Then 

$$
\lim_{k\to\infty}\|\nabla f(x^{[k]})\|=0,\qquad 
f(x^{[k+1]})\le f(x^{[k]})\ \ \text{for any initial }x^{[0]}.
$$

Under these assumptions, gradient descent either converges to $\inf_x f(x)$
if $f$ has no minimizer or to a stationary point $x^\ast$ with $\nabla f(x^\ast)=0.$

There exist examples where $x^\ast$ is a saddle point rather than a minimum, though in practice gradient methods often avoid saddles and find local minima.

### Math Proof
TODO

**Definition (Strong convexity).**  
$f$ is strongly convex with constant $\Lambda>0$ if for all $x,y\in\mathbb{R}^n:$

$$
f(x+y)\ \ge\ f(x)+\langle\nabla f(x),\,y\rangle+\frac{\Lambda}{2}\|y\|^2.
$$

**Theorem 2 (Convergence of gradient descent with constant step).**  
Let $f$ be differentiable and strongly convex with constant $\Lambda,$ and let the gradient be $L$-Lipschitz with $0<\lambda<\tfrac{2}{L}.$ Then there exists a unique $x^\ast=\arg\min f(x)$ and the iterates converge linearly: 

$$
\lim_{k\to\infty}x^{[k]}=x^\ast,\qquad
\|x^{[k]}-x^\ast\|\ \le\ q^{\,k}\,\|x^{[0]}-x^\ast\|,
$$

where $q=\max\{|1-\lambda\Lambda|,\ |1-\lambda L|\}.$

### Math Proof
TODO

## Gradient Method with Backtracking (Step Splitting)

In this variant of the gradient method, the step size $\lambda^{[k]}$ at iteration $k$ is chosen from the condition

$$
f\!\left(x^{[k+1]}\right)=f\!\left(x^{[k]}-\lambda^{[k]}\nabla f\!\left(x^{[k]}\right)\right)
\le
f\!\left(x^{[k]}\right)-\varepsilon\,\lambda^{[k]}\,\bigl\|\nabla f\!\left(x^{[k]}\right)\bigr\|^2,
$$

where $\varepsilon\in(0,1)$ is a fixed constant (often very small, e.g., $10^{-4}$).  
This inequality is the **Armijo (sufficient decrease) condition**.

---

### Backtracking procedure
Choose a contraction factor $\delta\in(0,1)$ and an initial trial step $\lambda^{[0]}>0$.  
For each outer iteration $k$:
1. Set the trial step $t\leftarrow \lambda^{[0]}$.
2. While the Armijo condition is **not** satisfied at $t$, shrink the step: $t \leftarrow \delta\, t$.
3. Accept $\lambda^{[k]} \leftarrow t$ and update

$$
x^{[k+1]} \=\ x^{[k]} - \lambda^{[k]}\nabla f\!\left(x^{[k]}\right)
$$

Backtracking guarantees that after a **finite** number of shrink operations the condition will hold, provided $f$ is continuously differentiable and bounded below along the search direction.

---

## Convergence 

- Under mild assumptions (e.g., $f$ is $L$-smooth), the Armijo backtracking gradient method **monotonically decreases** $f(x^{[k]})$ and every limit point is stationary: $\liminf_{k\to\infty}\|\nabla f(x^{[k]})\|=0$.
- If, in addition, $f$ is **mu-strongly convex** with $L$-Lipschitz gradient, then gradient descent with backtracking enjoys a **linear** rate:

$$
f(x^{[k]})-f(x^\star)\le \left(1-\frac{\mu}{c\,L}\right)^k\bigl(f(x^{[0]})-f(x^\star)\bigr)
$$

for some constant $c$ depending on $\varepsilon,\delta$ (typically $c\in[1,\,1/\delta]$).  
Intuitively, backtracking finds a step comparable to $1/L$ without knowing $L$ a priori.

### Proof:
TODO

## Steepest Descent (with Exact/Approximate Line Search)

The **steepest descent method** chooses the step length at iteration $k$ by minimizing the objective **along the negative gradient direction** that starts at the current point $x^{[k]}$.

Given $f:\mathbb{R}^n\to\mathbb{R}$ differentiable and $g^{[k]}=\nabla f(x^{[k]})$, define the ray

$$
\mathcal{L}_k \=\ \{\, x^{[k]} - \lambda\, g^{[k]} \|\ \lambda \ge 0 \,\}.
$$

The step length is

$$
\lambda^{[k]} \=\ \arg\min_{\lambda \ge 0} \ \phi_k(\lambda)
\quad\text{where}\quad
\phi_k(\lambda)= f\!\big(x^{[k]} - \lambda g^{[k]}\big).
$$

Then update

$$
x^{[k+1]} \=\ x^{[k]} - \lambda^{[k]} g^{[k]}.
$$

---

## Key properties

- **Orthogonality (with exact line search).**  
  If $\lambda^{[k]}$ minimizes $\phi_k$ exactly and $f$ is $C^1$, then

$$
  \nabla f(x^{[k+1]})^\top g^{[k]} \=\ 0,
$$

  i.e., consecutive **search directions are orthogonal** to the previous gradient.  
  For strictly convex quadratics this further implies that **successive gradients are orthogonal**.

- **Monotone decrease.**  
  Exact (or sufficiently accurate) line search guarantees

$$
  f(x^{[k+1]}) \le f(x^{[k]}).
$$

- **Computational trade-off.**  
  Each outer iteration requires (a) one gradient and (b) solving a **1-D optimization** problem for $\phi_k$.  
  This may mean fewer outer iterations than fixed-step gradient descent, but more function evaluations per iteration.

---

## Convergence highlights
TODO

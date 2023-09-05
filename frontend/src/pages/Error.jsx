import { Link } from "react-router-dom";
import nothingToSee from "../assets/images/nothingToSee.gif";

export default function Error() {
  return (
    <>
      <article>
        <h1>Page not found</h1>
        <img src={nothingToSee} />
        <Link to="/" className="link">Get back</Link>
      </article>
    </>
  );
}

/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The Quote class.
 */
@Entity
public class Quote {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  @OneToOne(cascade = CascadeType.PERSIST)
  public Person person;
  public String quote;

  /**
   * Only used by the object mappers.
   */
  @SuppressWarnings("unused")
  private Quote() {
    // for object mappers
  }

  /**
   * Initializes the quote with the given person and quote.
   *
   * @param person the person that created the quote
   * @param quote  the quote of the person
   */
  public Quote(Person person, String quote) {
    this.person = person;
    this.quote = quote;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * Returns a string representation of the Quote.
   *
   * @return a string representation of the Quote
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
  }
}